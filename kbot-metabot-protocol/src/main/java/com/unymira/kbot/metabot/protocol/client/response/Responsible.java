package com.unymira.kbot.metabot.protocol.client.response;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "responsible", "topics" })
public class Responsible
{
	@JsonProperty("responsible")
	private Boolean m_resposible;

	@JsonProperty("topics")
	private List<ResponsibleTopic> topics = null;

	public Responsible()
	{

	}

	public Responsible(final boolean p_responsible)
	{
		m_resposible = p_responsible;
	}

	public Responsible(final boolean p_responsible,
	                   final List<ResponsibleTopic> p_topics)
	{
		m_resposible = p_responsible;
		topics = p_topics;
	}

	@JsonGetter("responsible")
	public Boolean getResponsible()
	{
		return m_resposible == null ? Boolean.FALSE : m_resposible;
	}

	@JsonSetter("responsible")
	public void setResponsible(final Boolean responsible)
	{
		this.m_resposible = responsible;
	}

	@JsonGetter("topics")
	public List<ResponsibleTopic> getTopics()
	{
		return (topics == null) ? Collections.emptyList() : topics;
	}

	@JsonSetter("topics")
	public void setTopics(final List<ResponsibleTopic> topics)
	{
		this.topics = topics;
	}

	@Override
	public String toString()
	{
		return "Responsible [m_resposible=" + m_resposible + ", topics=" + topics + "]";
	}

}
