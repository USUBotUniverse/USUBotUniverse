package com.unymira.kbot.metabot.protocol.client.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponsibleTopic
{
	@JsonProperty(value = "name")
	private String m_name;

	@JsonProperty(value = "confidence")
	private Double m_confidence;

	public String getName()
	{
		return m_name;
	}

	public void setName(final String p_name)
	{
		m_name = p_name;
	}

	public Double getConfidence()
	{
		return m_confidence;
	}

	public void setConfidence(final double p_confidence)
	{
		m_confidence = Double.valueOf(p_confidence);
	}

	@Override
	public String toString()
	{
		return "ResponsibleTopic [m_name=" + m_name + ", m_confidence=" + m_confidence + "]";
	}
}
