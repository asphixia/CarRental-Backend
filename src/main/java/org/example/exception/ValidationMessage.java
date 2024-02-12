package org.example.exception;

import java.util.Map;

public record ValidationMessage(String message, Map<String, Object> args, Object value)
{
}