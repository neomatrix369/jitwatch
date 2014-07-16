package org.adoptopenjdk.jitwatch.util;

import java.util.Map;

import static org.adoptopenjdk.jitwatch.core.JITWatchConstants.*;

public final class InlineUtil
{
    /*
        Hide Utility Class Constructor
        Utility classes should not have a public or default constructor.
    */
    private InlineUtil() {
    }
    
	public static String buildInlineAnnotationText(boolean inlined, String reason, Map<String, String> callAttrs, Map<String, String> methodAttrs)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("Inlined: ");
		
		if (inlined)
		{
			builder.append("Yes, ");
		}
		else
		{
			builder.append("No, ");
		}
		
		builder.append(reason);
		
		if (callAttrs.containsKey(ATTR_COUNT))
		{
			builder.append(NEW_LINEFEED + "Count: ").append(callAttrs.get(ATTR_COUNT));
		}
		if (methodAttrs.containsKey(ATTR_IICOUNT))
		{
			builder.append(NEW_LINEFEED + "iicount: ").append(methodAttrs.get(ATTR_IICOUNT));
		}
		if (methodAttrs.containsKey(ATTR_BYTES))
		{
			builder.append(NEW_LINEFEED + "Bytes: ").append(methodAttrs.get(ATTR_BYTES));
		}
		if (callAttrs.containsKey(ATTR_PROF_FACTOR))
		{
			builder.append(NEW_LINEFEED + "Prof factor: ").append(callAttrs.get(ATTR_PROF_FACTOR));
		}

		return builder.toString();
	}
}
