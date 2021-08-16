package com.unymira.kbot.metabot.protocol.client.request;

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
@JsonPropertyOrder({ "application", "user", "attributes", "sessionId", "kvsSessionId", "asTeamMember", "smalltalk", "new" })
public class Session
{

	@JsonProperty("application")
	@Valid
	private Application application;

	@JsonProperty("user")
	@Valid
	private User user;

	@JsonProperty("attributes")
	@Valid
	private Attributes attributes;

	@JsonProperty("sessionId")
	private String sessionId;

	@JsonProperty("kvsSessionId")
	private String kvsSessionId;

	@JsonProperty("asTeamMember")
	private Boolean asTeamMember;

	@JsonProperty("smalltalk")
	private Boolean smalltalk;

	@JsonProperty("externalKey")
	private String externalKey;

	@JsonProperty("new")
	private Boolean _new;

	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("application")
	public Application getApplication()
	{
		return application;
	}

	@JsonProperty("application")
	public void setApplication(final Application application)
	{
		this.application = application;
	}

	public Session withApplication(final Application application)
	{
		this.application = application;
		return this;
	}

	@JsonProperty("user")
	public User getUser()
	{
		return user;
	}

	@JsonProperty("user")
	public void setUser(final User user)
	{
		this.user = user;
	}

	public Session withUser(final User user)
	{
		this.user = user;
		return this;
	}

	@JsonProperty("attributes")
	public Attributes getAttributes()
	{
		return attributes;
	}

	@JsonProperty("attributes")
	public void setAttributes(final Attributes attributes)
	{
		this.attributes = attributes;
	}

	public Session withAttributes(final Attributes attributes)
	{
		this.attributes = attributes;
		return this;
	}

	@JsonProperty("sessionId")
	public String getSessionId()
	{
		return sessionId;
	}

	@JsonProperty("sessionId")
	public void setSessionId(final String sessionId)
	{
		this.sessionId = sessionId;
	}

	public Session withSessionId(final String sessionId)
	{
		this.sessionId = sessionId;
		return this;
	}

	@JsonProperty("kvsSessionId")
	public String getKvsSessionId()
	{
		return kvsSessionId;
	}

	@JsonProperty("kvsSessionId")
	public void setKvsSessionId(final String kvsSessionId)
	{
		this.kvsSessionId = kvsSessionId;
	}

	public Session withKvsSessionId(final String kvsSessionId)
	{
		this.kvsSessionId = kvsSessionId;
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

	public Session withExternalKey(final String externalKey)
	{
		this.externalKey = externalKey;
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

	public Session withSmallTalk(final Boolean smalltalk)
	{
		this.smalltalk = smalltalk;
		return this;
	}

	@JsonProperty("asTeamMember")
	public Boolean getAsTeamMember()
	{
		return asTeamMember;
	}

	@JsonProperty("asTeamMember")
	public void setAsTeamMember(final Boolean asTeamMember)
	{
		this.asTeamMember = asTeamMember;
	}

	public Session withAsTeamMember(final Boolean asTeamMember)
	{
		this.asTeamMember = asTeamMember;
		return this;
	}

	@JsonProperty("new")
	public Boolean getNew()
	{
		return _new;
	}

	@JsonProperty("new")
	public void setNew(final Boolean _new)
	{
		this._new = _new;
	}

	public Session withNew(final Boolean _new)
	{
		this._new = _new;
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
		if (value == null)
		{
			this.additionalProperties.remove(name);
		}
		else
		{
			this.additionalProperties.put(name, value);
		}
	}

	public Session withAdditionalProperty(final String name, final Object value)
	{
		this.additionalProperties.put(name, value);
		return this;
	}
}
