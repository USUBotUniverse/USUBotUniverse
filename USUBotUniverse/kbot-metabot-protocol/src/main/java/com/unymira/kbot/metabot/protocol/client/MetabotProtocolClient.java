package com.unymira.kbot.metabot.protocol.client;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unymira.kbot.metabot.protocol.client.request.Application;
import com.unymira.kbot.metabot.protocol.client.request.Attributes;
import com.unymira.kbot.metabot.protocol.client.request.Payload;
import com.unymira.kbot.metabot.protocol.client.request.Request;
import com.unymira.kbot.metabot.protocol.client.request.RequestRootObject;
import com.unymira.kbot.metabot.protocol.client.request.RequestType;
import com.unymira.kbot.metabot.protocol.client.request.Session;
import com.unymira.kbot.metabot.protocol.client.request.User;
import com.unymira.kbot.metabot.protocol.client.response.ResponseRootObject;
import com.unymira.kbot.metabot.protocol.client.topics.TopicRequestRootObject;
import com.unymira.kbot.metabot.protocol.client.topics.TopicResponseRootObject;

public class MetabotProtocolClient implements Closeable
{
	private static final Logger LOG = LoggerFactory.getLogger(MetabotProtocolClient.class);

//	private static DateTimeFormatter ISO_FORMATTER = new DateTimeFormatterBuilder()
//	        .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME).appendPattern("xxx").toFormatter();

	private String m_applicationId = "KBot-Member";
	private String m_userId = "KBot";

	private String m_locale = Locale.getDefault().getLanguage();

	private String m_currentSessionId;

	private String m_keyValueStoreSessionId;

	private Boolean m_asTeamMember;

	private Boolean m_smallTalk;

	private Boolean m_debugMode;

	private String m_externalKey;

	private HttpClient m_httpClient;
	private URI m_botUri;
	private String m_authScheme = null;
	private AtomicLong m_counter = new AtomicLong();

	public static MetabotProtocolClient newMetabotProtocolClient(final URI p_uri)
	{
		return new MetabotProtocolClient(p_uri);
	}

	private MetabotProtocolClient(final URI p_uri)
	{
		if (p_uri == null)
		{
			throw new IllegalArgumentException("URI must not be null!");
		}

		m_botUri = p_uri;
		m_httpClient = HttpClient.newHttpClient();
	}

	public void withBasicAuthentication(final String p_username, final String p_password)
	{
		if (p_username == null || p_password == null)
		{
			throw new IllegalArgumentException("Username and password must not be null!");
		}

		m_authScheme = "Basic: " + Base64.getEncoder().encodeToString((p_username + ':' + p_password).getBytes(StandardCharsets.UTF_8));
	}

	public void withBearerAuthentication(final String p_token)
	{
		if (p_token == null)
		{
			throw new IllegalArgumentException("Token must not be null!");
		}
		m_authScheme = "Bearer: " + p_token;
	}

	public MetabotProtocolClient withLocale(final String p_locale)
	{
		m_locale = p_locale;
		return this;
	}

	public MetabotProtocolClient withApplicationId(final String p_applicationId)
	{
		m_applicationId = p_applicationId;
		return this;
	}

	public MetabotProtocolClient withUserId(final String p_userId)
	{
		m_userId = p_userId;
		return this;
	}

	public MetabotProtocolClient withSessionId(final String p_sessionId)
	{
		m_currentSessionId = p_sessionId;
		return this;
	}

	public MetabotProtocolClient witKVStoreSessionId(final String p_keyValueStoreSessionID)
	{
		this.m_keyValueStoreSessionId = p_keyValueStoreSessionID;
		return this;
	}

	public MetabotProtocolClient withAsTeamMember(final boolean p_asTeamMember)
	{
		m_asTeamMember = Boolean.valueOf(p_asTeamMember);
		return this;
	}

	public MetabotProtocolClient withExternalKey(final String p_externalKey)
	{
		m_externalKey = p_externalKey;
		return this;
	}

	public MetabotProtocolClient withSmallTalk(final boolean p_smallTalk)
	{
		m_smallTalk = Boolean.valueOf(p_smallTalk);
		return this;
	}

	public MetabotProtocolClient withDebugMode(final boolean p_debugMode)
	{
		m_debugMode = p_debugMode;
		return this;
	}

	public boolean isClosed()
	{
		return m_httpClient == null;
	}

	@Override
	public void close()
	{
		m_httpClient = null;
		m_botUri = null;
	}

	public ResponseRootObject startSession() throws MetabotProtocolException
	{
		try
		{
			Session l_session = new Session();
			l_session.setSessionId(m_currentSessionId);
			l_session.withKvsSessionId(m_keyValueStoreSessionId);
			l_session.withAsTeamMember(m_asTeamMember);
			l_session.withSmallTalk(m_smallTalk);
			l_session.withExternalKey(m_externalKey);
			Attributes a = new Attributes();
			a.withAdditionalProperty("debugMode", m_debugMode);
			l_session.withAttributes(a);
			l_session.setNew(Boolean.TRUE);

			l_session.withApplication(new Application().withApplicationId(m_applicationId));
			l_session.withUser(new User().withUserId(m_userId));

			Request l_request = new Request().withType(RequestType.COMMAND)
			        .withRequestId(Long.toString(m_counter.getAndIncrement()))
			        .withLocale(m_locale)
			        .withTimestamp(OffsetDateTime.now())
			        .withSmallTalk(Boolean.valueOf(m_smallTalk))
			        .withPayload(new Payload().withType("startSession"));

			RequestRootObject l_jsonRoot = new RequestRootObject().withSession(l_session).withRequest(l_request);

			HttpResponse<String> l_response = sendRequest(l_jsonRoot);

			if (l_response.statusCode() == 200)
			{
				return readResponse(l_response.body());
			}
			else
			{
				throw new MetabotProtocolException(
				        "Could not establish new session! Got StatusCode=" + l_response.statusCode());
			}
		}
		catch (IllegalArgumentException | IOException | InterruptedException ex)
		{
			throw new MetabotProtocolException("Could not establish new session on " + m_botUri + "!", ex);
		}
	}

	public ResponseRootObject finishSession() throws MetabotProtocolException
	{
		try
		{
			Session l_session = new Session();
			l_session.setSessionId(m_currentSessionId);
			l_session.withKvsSessionId(m_keyValueStoreSessionId);
			l_session.withAsTeamMember(m_asTeamMember);
			Attributes a = new Attributes();
			a.withAdditionalProperty("debugMode", m_debugMode);
			l_session.withAttributes(a);
			l_session.withSmallTalk(m_smallTalk);
			l_session.setNew(Boolean.FALSE);
			l_session.withApplication(new Application().withApplicationId(m_applicationId));
			l_session.withUser(new User().withUserId(m_userId));

			Request l_request = new Request().withType(RequestType.COMMAND)
			        .withRequestId(Long.toString(m_counter.getAndIncrement()))
			        .withLocale(m_locale)
			        .withTimestamp(OffsetDateTime.now())
			        .withSmallTalk(Boolean.valueOf(m_smallTalk))
			        .withPayload(new Payload().withType("endSession"));

			RequestRootObject l_jsonRoot = new RequestRootObject().withSession(l_session).withRequest(l_request);

			HttpResponse<String> l_response = sendRequest(l_jsonRoot);

			if (l_response.statusCode() == 200)
			{
				return readResponse(l_response.body());
			}
			else
			{
				throw new MetabotProtocolException(
				        "Could not finish session! Got StatusCode=" + l_response.statusCode());
			}
		}
		catch (IllegalArgumentException | IOException | InterruptedException ex)
		{
			throw new MetabotProtocolException("Could not finish session on " + m_botUri + "!", ex);
		}
	}

	public ResponseRootObject resetSession() throws MetabotProtocolException
	{
		try
		{
			Session l_session = new Session();
			l_session.withSessionId(m_currentSessionId);
			l_session.withKvsSessionId(m_keyValueStoreSessionId);
			l_session.withAsTeamMember(m_asTeamMember);
			l_session.withSmallTalk(m_smallTalk);
			l_session.withExternalKey(m_externalKey);
			l_session.setNew(Boolean.FALSE);
			l_session.withApplication(new Application().withApplicationId(m_applicationId));
			l_session.withUser(new User().withUserId(m_userId));

			Request l_request = new Request().withType(RequestType.COMMAND)
			        .withRequestId(Long.toString(m_counter.getAndIncrement()))
			        .withLocale(m_locale)
			        .withTimestamp(OffsetDateTime.now())
			        .withSmallTalk(Boolean.valueOf(m_smallTalk))
			        .withExternalKey(m_externalKey)
			        .withPayload(new Payload().withType("resetSession"));

			RequestRootObject l_jsonRoot = new RequestRootObject().withSession(l_session).withRequest(l_request);

			HttpResponse<String> l_response = sendRequest(l_jsonRoot);

			if (l_response.statusCode() == 200)
			{
				return readResponse(l_response.body());
			}
			else
			{
				throw new MetabotProtocolException(
				        "Could not reset session! Got StatusCode=" + l_response.statusCode());
			}
		}
		catch (IllegalArgumentException | IOException | InterruptedException ex)
		{
			throw new MetabotProtocolException("Could not reset session " + m_botUri + "!", ex);
		}
	}

	public ResponseRootObject communicate(final Payload p_request) throws MetabotProtocolException
	{
		try
		{
			Session l_session = new Session();
			l_session.withSessionId(m_currentSessionId);
			l_session.withKvsSessionId(m_keyValueStoreSessionId);
			l_session.withAsTeamMember(m_asTeamMember);
			Attributes a = new Attributes();
			a.withAdditionalProperty("debugMode", m_debugMode);
			l_session.withAttributes(a);
			l_session.withSmallTalk(m_smallTalk);
			l_session.withExternalKey(m_externalKey);
			l_session.setNew(Boolean.FALSE);
			l_session.withApplication(new Application().withApplicationId(m_applicationId));
			l_session.withUser(new User().withUserId(m_userId));

			Request l_request = new Request().withType(RequestType.MESSAGE)

			        .withRequestId(Long.toString(m_counter.getAndIncrement()))
			        .withLocale(m_locale)
			        .withTimestamp(OffsetDateTime.now())
			        .withSmallTalk(Boolean.valueOf(m_smallTalk))
			        .withExternalKey(m_externalKey)
			        .withPayload(p_request);

			RequestRootObject l_jsonRoot = new RequestRootObject().withSession(l_session).withRequest(l_request);

			HttpResponse<String> l_response = sendRequest(l_jsonRoot);

			if (l_response.statusCode() == 200)
			{
				return readResponse(l_response.body());
			}
			else
			{
				throw new MetabotProtocolException("Could not communicate to " + m_botUri + "! Got StatusCode=" + l_response.statusCode());
			}
		}
		catch (IllegalArgumentException | IOException | InterruptedException ex)
		{
			throw new MetabotProtocolException("Could not communicate " + m_botUri + "!", ex);
		}
	}

	public ResponseRootObject askIsAvaiable() throws MetabotProtocolException
	{
		try
		{
			Session l_session = new Session();
			l_session.withSessionId(m_currentSessionId);
			l_session.withKvsSessionId(m_keyValueStoreSessionId);
			l_session.withAsTeamMember(m_asTeamMember);

			Attributes a = new Attributes();
			a.withAdditionalProperty("debugMode", m_debugMode);

			l_session.withAttributes(a);

			l_session.withSmallTalk(m_smallTalk);
			l_session.withExternalKey(m_externalKey);
			l_session.setNew(Boolean.FALSE);
			l_session.withApplication(new Application().withApplicationId(m_applicationId));
			l_session.withUser(new User().withUserId(m_userId));

			Request l_request = new Request().withType(RequestType.COMMAND)
			        .withRequestId(Long.toString(m_counter.getAndIncrement()))
			        .withLocale(m_locale)
			        .withTimestamp(OffsetDateTime.now())
			        .withSmallTalk(m_smallTalk)
			        .withExternalKey(m_externalKey)
			        .withPayload(new Payload().withType("available"));

			RequestRootObject l_jsonRoot = new RequestRootObject().withSession(l_session).withRequest(l_request);

			HttpResponse<String> l_response = sendRequest(l_jsonRoot);

			if (l_response.statusCode() == 200)
			{
				String l_jsonBody = l_response.body();
				return readResponse(l_jsonBody);
			}
			else
			{
				throw new MetabotProtocolException("Could not execute request to " + m_botUri + "! Got StatusCode=" + l_response.statusCode());
			}
		}
		catch (IllegalArgumentException | IOException | InterruptedException ex)
		{
			throw new MetabotProtocolException("Could not establish connection " + m_botUri + "!", ex);
		}
	}

	public ResponseRootObject askWhoFeelsResponsible(final String p_input) throws MetabotProtocolException
	{
		try
		{
			Session l_session = new Session();
			l_session.withSessionId(m_currentSessionId);
			l_session.withKvsSessionId(m_keyValueStoreSessionId);
			l_session.withAsTeamMember(m_asTeamMember);

			Attributes a = new Attributes();
			a.withAdditionalProperty("debugMode", m_debugMode);

			l_session.withAttributes(a);
			l_session.withSmallTalk(m_smallTalk);
			l_session.withExternalKey(m_externalKey);
			l_session.setNew(Boolean.FALSE);
			l_session.withApplication(new Application().withApplicationId(m_applicationId));
			l_session.withUser(new User().withUserId(m_userId));

			Request l_request = new Request().withType(RequestType.COMMAND) // METABOT
			        .withRequestId(Long.toString(m_counter.getAndIncrement()))
			        .withLocale(m_locale)
			        .withTimestamp(OffsetDateTime.now())
			        .withSmallTalk(Boolean.valueOf(m_smallTalk))
			        .withExternalKey(m_externalKey)
			        .withPayload(new Payload().withType("responsible").withText(p_input));

			RequestRootObject l_jsonRoot = new RequestRootObject().withSession(l_session).withRequest(l_request);

			HttpResponse<String> l_response = sendRequest(l_jsonRoot);

			if (l_response.statusCode() == 200)
			{
				return readResponse(l_response.body());
			}
			else
			{
				throw new MetabotProtocolException("Could not execute request to " + m_botUri + "! Got StatusCode=" + l_response.statusCode());
			}
		}
		catch (IllegalArgumentException | IOException | InterruptedException ex)
		{
			throw new MetabotProtocolException("Could not establish connection " + m_botUri + "!", ex);
		}
	}

	public TopicResponseRootObject askForTopics(final String p_input) throws MetabotProtocolException
	{
		try
		{
			TopicRequestRootObject l_jsonRoot = new TopicRequestRootObject().withInput(p_input);

			HttpResponse<String> l_response = sendRequest(l_jsonRoot);

			if (l_response.statusCode() == 200)
			{
				return readTopicResponse(l_response.body());
			}
			else
			{
				throw new MetabotProtocolException("Could not execute request to " + m_botUri + "! Got StatusCode=" + l_response.statusCode());
			}
		}
		catch (IllegalArgumentException | IOException | InterruptedException ex)
		{
			throw new MetabotProtocolException("Could not establish connection!" + m_botUri + "!", ex);
		}
	}

	private HttpResponse<String> sendRequest(final Object p_request) throws IOException, InterruptedException, MetabotProtocolException
	{
		ObjectMapper l_mapper = new ObjectMapper();
		String l_payload = l_mapper.writeValueAsString(p_request);

		if (isClosed())
		{
			throw new MetabotProtocolException("Could not send Request! Client is/was closed before.");
		}

		if (LOG.isDebugEnabled())
		{
			LOG.debug("Send request to {}", m_botUri);
		}

		var l_builder = HttpRequest.newBuilder().uri(m_botUri)
		        .POST(HttpRequest.BodyPublishers.ofString(l_payload, StandardCharsets.UTF_8))
		        .header("Content-Type", "application/json");
		if (m_authScheme != null)
		{
			l_builder.setHeader("Authorization", m_authScheme);
		}
		var l_request = l_builder.build();

		var l_responseHandler = HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8);

		return m_httpClient.send(l_request, l_responseHandler);
	}

	private ResponseRootObject readResponse(final String p_jsonString) throws JsonParseException, JsonMappingException, IOException
	{
		if (LOG.isTraceEnabled())
		{
			LOG.trace("Response-Body:\n{}", p_jsonString);
		}

		ObjectMapper l_mapper = new ObjectMapper();
		ResponseRootObject l_response = l_mapper.readValue(p_jsonString, ResponseRootObject.class);

		if (l_response.getSessionAttributes() != null)
		{
			m_currentSessionId = l_response.getSessionAttributes().getSessionId();
		}

		if (l_response.getResponse() != null)
		{
			if (Boolean.TRUE.equals(l_response.getResponse().getShouldEndSession()))
			{
				// TODO handle end session
			}
		}

		return l_response;
	}

	private TopicResponseRootObject readTopicResponse(final String p_jsonString) throws JsonParseException, JsonMappingException, IOException
	{
		if (LOG.isTraceEnabled())
		{
			LOG.trace("Response-Body:\n{}", p_jsonString);
		}

		ObjectMapper l_mapper = new ObjectMapper();
		TopicResponseRootObject l_response = l_mapper.readValue(p_jsonString, TopicResponseRootObject.class);

		return l_response;
	}

	public static class MetabotProtocolException extends Exception
	{
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public MetabotProtocolException(final String message,
		                                final Throwable cause)
		{
			super(message, cause);
		}

		public MetabotProtocolException(final String message)
		{
			super(message);
		}

		public MetabotProtocolException(final Throwable cause)
		{
			super(cause);
		}
	}
}
