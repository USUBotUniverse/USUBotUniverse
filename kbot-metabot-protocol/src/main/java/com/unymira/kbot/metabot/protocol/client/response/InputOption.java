package com.unymira.kbot.metabot.protocol.client.response;

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
@JsonPropertyOrder({ "label", "value", "styleInfo", "metadata" })
public class InputOption
{

	@JsonProperty("label")
	private String label;
	@JsonProperty("value")
	private String value;
	@JsonProperty("styleInfo")
	private String styleInfo;
	@JsonProperty("metadata")
	@Valid
	private Metadata_ metadata;
	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("label")
	public String getLabel()
	{
		return label;
	}

	@JsonProperty("label")
	public void setLabel(final String label)
	{
		this.label = label;
	}

	public InputOption withLabel(final String label)
	{
		this.label = label;
		return this;
	}

	@JsonProperty("value")
	public String getValue()
	{
		return value;
	}

	@JsonProperty("value")
	public void setValue(final String value)
	{
		this.value = value;
	}

	public InputOption withValue(final String value)
	{
		this.value = value;
		return this;
	}

	@JsonProperty("styleInfo")
	public String getStyleInfo()
	{
		return styleInfo;
	}

	@JsonProperty("styleInfo")
	public void setStyleInfo(final String styleInfo)
	{
		this.styleInfo = styleInfo;
	}

	public InputOption withStyleInfo(final String styleInfo)
	{
		this.styleInfo = styleInfo;
		return this;
	}

	@JsonProperty("metadata")
	public Metadata_ getMetadata()
	{
		return metadata;
	}

	@JsonProperty("metadata")
	public void setMetadata(final Metadata_ metadata)
	{
		this.metadata = metadata;
	}

	public InputOption withMetadata(final Metadata_ metadata)
	{
		this.metadata = metadata;
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

	public InputOption withAdditionalProperty(final String name, final Object value)
	{
		this.additionalProperties.put(name, value);
		return this;
	}

}
