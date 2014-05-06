/*
 * Copyright (c) 2013, 2014 Chris Newland.
 * Licensed under https://github.com/AdoptOpenJDK/jitwatch/blob/master/LICENSE-BSD
 * Instructions: https://github.com/AdoptOpenJDK/jitwatch/wiki
 */
package com.chrisnewland.jitwatch.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chrisnewland.jitwatch.core.JITWatchConstants.*;

public class Tag
{
	private String name;
	private Map<String, String> attrs = new HashMap<>();
	private List<Tag> children = new ArrayList<>();
	private Tag parent = null;
	private boolean selfClosing = false;
	private String textContent = null;

	private static final String INDENT = "  ";

	public Tag(String name, Map<String, String> attrs, boolean selfClosing)
	{
		this.name = name;
		this.attrs = attrs;
		this.selfClosing = selfClosing;
	}

	public void addTextContent(String text)
	{
		if (textContent == null)
		{
			textContent = text;
		}
		else
		{
			textContent += text;
		}
	}

	public String getTextContent()
	{
		return textContent;
	}

	public void addChild(Tag child)
	{
		child.setParent(this);
		children.add(child);
	}

	public List<Tag> getChildren()
	{
		return children;
	}
	
	public boolean isSelfClosing()
	{
		return selfClosing;
	}

	public Tag getFirstNamedChild(String name)
	{
		List<Tag> namedChildren = getNamedChildren(name);

		if (namedChildren.size() > 0)
		{
			return namedChildren.get(0);
		}
		else
		{
			return null;
		}
	}

	public List<Tag> getNamedChildren(String name)
	{
		List<Tag> result = new ArrayList<>();

		for (Tag child : children)
		{
			if (child.getName().equals(name))
			{
				result.add(child);
			}
		}

		return result;
	}

	public List<Tag> getNamedChildrenWithAttribute(String tagName, String attrName, String attrValue)
	{
		List<Tag> result = new ArrayList<>();

		for (Tag child : children)
		{
			if ((child.getName().equals(tagName)) &&
                (child.getAttrs().containsKey(attrName) &&
                 child.getAttribute(attrName).equals(attrValue)))
            {
                result.add(child);
            }
		}

		return result;
	}

	public Tag getParent()
	{
		return parent;
	}

	public void setParent(Tag parent)
	{
		this.parent = parent;
	}

	public String getName()
	{
		return name;
	}

	public Map<String, String> getAttrs()
	{
		return attrs;
	}
	
	public String getAttribute(String name)
	{
		return attrs.get(name);
	}

	private int getDepth(Tag tag)
	{
		if (tag.getParent() != null)
		{
			return 1 + getDepth(tag.getParent());
		}
		else
		{
			return 0;
		}
	}

	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		int myDepth = getDepth(this);

		for (int i = 0; i < myDepth; i++)
		{
			builder.append(INDENT);
		}

		builder.append(C_OPEN_ANGLE).append(name);

		if (attrs.size() > 0)
		{
			for (Map.Entry<String, String> entry : attrs.entrySet())
			{
				builder.append(C_SPACE).append(entry.getKey()).append(C_EQUALS).append(C_DOUBLE_QUOTE);
				builder.append(entry.getValue()).append(C_DOUBLE_QUOTE);
			}
		}

		if (selfClosing)
		{
			builder.append(C_SLASH).append(C_CLOSE_ANGLE).append(C_NEWLINE);
		}
		else
		{
			if (children.size() > 0)
			{
				builder.append(C_CLOSE_ANGLE).append(C_NEWLINE);

				for (Tag child : children)
				{
					builder.append(child.toString());
				}
			}
			else
			{
				builder.append(C_CLOSE_ANGLE).append(C_NEWLINE);

				if (textContent != null)
				{
					for (int i = 0; i < myDepth; i++)
					{
						builder.append(INDENT);
					}
					
					builder.append(textContent).append(C_NEWLINE);
				}
			}

			for (int i = 0; i < myDepth; i++)
			{
				builder.append(INDENT);
			}

			builder.append(C_OPEN_ANGLE).append(C_SLASH);
			builder.append(name).append(C_CLOSE_ANGLE).append(C_NEWLINE);
		}

		return builder.toString();
	}
}