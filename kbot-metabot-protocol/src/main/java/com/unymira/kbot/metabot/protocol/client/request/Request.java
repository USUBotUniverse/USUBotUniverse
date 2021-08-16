package com.unymira.kbot.metabot.protocol.client.request;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "type", "requestId", "locale", "timestamp", "payload", "smalltalk", "externalKey" })
public class Request
{
	private static DateTimeFormatter ISO_FORMATTER = new DateTimeFormatterBuilder().append(DateTimeFormatter.ISO_LOCAL_DATE_TIME).appendPattern("xxx").toFormatter();

	@JsonProperty("type")
	private RequestType type;

	@JsonProperty("requestId")
	private String requestId;

	@JsonProperty("locale")
	private String locale;

	@JsonProperty("timestamp")
	private String timestamp;

	@JsonProperty("smalltalk")
	private Boolean smalltalk;

	@JsonProperty("externalKey")
	private String externalKey;

	@JsonProperty("payload")
	@Valid
	private Payload payload;

	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("type")
	public RequestType getType()
	{
		return type;
	}

	@JsonProperty("type")
	public void setType(final RequestType p_type)
	{
		this.type = p_type;
	}

	public Request withType(final RequestType p_type)
	{
		this.type = p_type;
		return this;
	}

	@JsonProperty("requestId")
	public String getRequestId()
	{
		return requestId;
	}

	@JsonProperty("requestId")
	public void setRequestId(final String requestId)
	{
		this.requestId = requestId;
	}

	public Request withRequestId(final String requestId)
	{
		this.requestId = requestId;
		return this;
	}

	@JsonProperty("locale")
	public String getLocale()
	{
		return locale;
	}

	@JsonProperty("locale")
	public void setLocale(final String locale)
	{
		this.locale = locale;
	}

	public Request withLocale(final String locale)
	{
		this.locale = locale;
		return this;
	}

	@JsonProperty("timestamp")
	public String getTimestamp()
	{
		return timestamp;
	}

	@JsonProperty("timestamp")
	public void setTimestamp(final String timestamp)
	{
		this.timestamp = timestamp;
	}

	public Request withTimestamp(final OffsetDateTime timestamp)
	{
		this.timestamp = ISO_FORMATTER.format(timestamp);
		return this;
	}

	public Request withCurrentTimestamp()
	{
		this.timestamp = ISO_FORMATTER.format(OffsetDateTime.now());
		return this;
	}

	@JsonProperty("smalltalk")
	public Boolean getSmalltalk()
	{
		return smalltalk;
	}

	@JsonProperty("smalltalk")
	public void setSmalltalk(final Boolean smalltalk)
	{
		this.smalltalk = smalltalk;
	}

	public Request withSmallTalk(final Boolean smalltalk)
	{
		this.smalltalk = smalltalk;
		return this;
	}

	@JsonProperty("externalKey")
	public String getExternalKey()
	{
		return externalKey;
	}

	@JsonProperty("externalKey")
	public void setExternalKey(final String externalKey)
	{
		this.externalKey = externalKey;
	}

	public Request withExternalKey(final String externalKey)
	{
		this.externalKey = externalKey;
		return this;
	}

	@JsonProperty("payload")
	public Payload getPayload()
	{
		return payload;
	}

	@JsonProperty("payload")
	public void setPayload(final Payload payload)
	{
		this.payload = payload;
	}

	public Request withPayload(final Payload payload)
	{
		this.payload = payload;
		return this;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties()
	{
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(final String name, final Object value)
	{
		this.additionalProperties.put(name, value);
	}

	public Request withAdditionalProperty(final String name, final Object value)
	{
		this.additionalProperties.put(name, value);
		return this;
	}
}
