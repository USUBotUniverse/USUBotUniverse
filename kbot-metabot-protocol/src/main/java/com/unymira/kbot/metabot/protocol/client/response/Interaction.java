package com.unymira.kbot.metabot.protocol.client.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "multiSelectAllowed", "inputType", "inputOptions", "inputFieldActive", "allowOptionFiltering", "inputHint", "dialog", "yesorno" })
public class Interaction
{

	@JsonProperty("multiSelectAllowed")
	private Boolean multiSelectAllowed;

	@JsonProperty("allowOptionFiltering")
	private Boolean allowOptionFiltering;

	@JsonProperty("inputType")
	private String inputType;

	@JsonProperty("inputOptions")
	@Valid
	private List<InputOption> inputOptions = null;

	@JsonProperty("inputFieldActive")
	private Boolean inputFieldActive;

	@JsonProperty("inputFieldVisible")
	private Boolean inputFieldVisible;

	@JsonProperty("inputFieldPattern")
	private String inputFieldPattern;

	@JsonProperty("inputHint")
	private String inputHint;

	@JsonProperty("allowAutoComplete")
	private Boolean allowAutoComplete;

	@JsonProperty("showResultCount")
	private Integer showResultCount;

	@JsonProperty("dialog")
	private boolean isInGuidedDialog;

	@JsonProperty("yesorno")
	private boolean isYesOrNoQuestion;

	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("multiSelectAllowed")
	public Boolean getMultiSelectAllowed()
	{
		return multiSelectAllowed;
	}

	@JsonProperty("multiSelectAllowed")
	public void setMultiSelectAllowed(final Boolean multiSelectAllowed)
	{
		this.multiSelectAllowed = multiSelectAllowed;
	}

	public Interaction withMultiSelectAllowed(final Boolean multiSelectAllowed)
	{
		this.multiSelectAllowed = multiSelectAllowed;
		return this;
	}

	@JsonProperty("allowOptionFiltering")
	public Boolean getAllowOptionFiltering()
	{
		return allowOptionFiltering;
	}

	@JsonProperty("allowOptionFiltering")
	public void setAllowOptionFiltering(final Boolean allowOptionFiltering)
	{
		this.allowOptionFiltering = allowOptionFiltering;
	}
	public Interaction withAllowOptionFiltering(final Boolean allowOptionFiltering)
	{
		this.allowOptionFiltering = allowOptionFiltering;
		return this;
	}

	@JsonProperty("inputType")
	public String getInputType()
	{
		return inputType;
	}

	@JsonProperty("inputType")
	public void setInputType(final String inputType)
	{
		this.inputType = inputType;
	}

	public Interaction withInputType(final String inputType)
	{
		this.inputType = inputType;
		return this;
	}

	@JsonProperty("inputOptions")
	public List<InputOption> getInputOptions()
	{
		return inputOptions;
	}

	@JsonProperty("inputOptions")
	public void setInputOptions(final List<InputOption> inputOptions)
	{
		this.inputOptions = inputOptions;
	}

	public Interaction withInputOptions(final List<InputOption> inputOptions)
	{
		this.inputOptions = inputOptions;
		return this;
	}

	@JsonProperty("inputFieldActive")
	public Boolean getInputFieldActive()
	{
		return inputFieldActive;
	}

	@JsonProperty("inputFieldActive")
	public void setInputFieldActive(final Boolean inputFieldActive)
	{
		this.inputFieldActive = inputFieldActive;
	}

	public Interaction withInputFieldActive(final Boolean inputFieldActive)
	{
		this.inputFieldActive = inputFieldActive;
		return this;
	}

	@JsonProperty("inputFieldVisible")
	public Boolean getInputFieldVisible()
	{
		return inputFieldVisible;
	}

	@JsonProperty("inputFieldVisible")
	public void setInputFieldVisible(final Boolean inputFieldVisible)
	{
		this.inputFieldVisible = inputFieldVisible;
	}

	public Interaction withInputFieldVisible(final Boolean inputFieldVisible)
	{
		this.inputFieldVisible = inputFieldVisible;
		return this;
	}

	@JsonProperty("inputFieldPattern")
	public String getInputFieldPattern()
	{
		return inputFieldPattern;
	}

	@JsonProperty("inputFieldPattern")
	public void setInputFieldPattern(final String inputFieldPattern)
	{
		this.inputFieldPattern = inputFieldPattern;
	}

	public Interaction withInputFieldPattern(final String inputFieldPattern)
	{
		this.inputFieldPattern = inputFieldPattern;
		return this;
	}

	@JsonProperty("inputHint")
	public String getInputHint()
	{
		return inputHint;
	}

	@JsonProperty("inputHint")
	public void setInputHint(final String inputHint)
	{
		this.inputHint = inputHint;
	}

	public Interaction withInputHint(final String inputHint)
	{
		this.inputHint = inputHint;
		return this;
	}

	@JsonProperty("allowAutoComplete")
	public Boolean getAllowAutoComplete()
	{
		return allowAutoComplete;
	}

	@JsonProperty("allowAutoComplete")
	public void setAllowAutoComplete(final Boolean allowAutoComplete)
	{
		this.allowAutoComplete = allowAutoComplete;
	}

	public Interaction withAllowAutoComplete(final Boolean allowAutoComplete)
	{
		this.allowAutoComplete = allowAutoComplete;
		return this;
	}

	@JsonProperty("showResultCount")
	public Integer getShowResultCount()
	{
		return showResultCount;
	}

	@JsonProperty("showResultCount")
	public void setShowResultCount(final Integer showResultCount)
	{
		this.showResultCount = showResultCount;
	}

	public Interaction withShowResultCount(final Integer showResultCount)
	{
		this.showResultCount = showResultCount;
		return this;
	}
	@JsonProperty("dialog")
	public boolean isInGuidedDialog()
	{
		return isInGuidedDialog;
	}

	public void setInGuidedDialog(boolean inGuidedDialog)
	{
		this.isInGuidedDialog = inGuidedDialog;
	}

	@JsonProperty("yesorno")
	public boolean isYesOrNoQuestion()
	{
		return isYesOrNoQuestion;
	}

	public void setYesOrNoQuestion(boolean isYesOrNoQuestion)
	{
		this.isYesOrNoQuestion = isYesOrNoQuestion;
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

	public Interaction withAdditionalProperty(final String name, final Object value)
	{
		this.additionalProperties.put(name, value);
		return this;
	}

}
