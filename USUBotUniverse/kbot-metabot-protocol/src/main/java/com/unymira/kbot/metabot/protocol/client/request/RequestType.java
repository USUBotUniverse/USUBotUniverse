package com.unymira.kbot.metabot.protocol.client.request;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RequestType
{
	COMMAND("command"), MESSAGE("message"), METABOT("metabot");

	private String name;

	RequestType(final String p_name)
	{
		name = p_name;
	}

	@JsonValue
	public String getName()
	{
		return name;
	}
}
