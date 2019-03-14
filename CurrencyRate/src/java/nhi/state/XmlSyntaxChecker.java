/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.state;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author admin
 */
import static nhi.state.SyntaxState.*;

public class XmlSyntaxChecker {

    private Character quote;

    private String conver(Map<String, String> attributes) {
        if (attributes.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String value = entry.getValue()
                    .replace("&", "&amp;")
                    .replaceAll("\"", "&quot;")
                    .replaceAll("'", "&apos;")
                    .replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;");

            builder.append(entry.getKey())
                    .append("=")
                    .append("\"").append(value).append("\"")
                    .append(" ");
        }
        String result = builder.toString().trim();
        if (!result.equals("")) {
            result = " " + result;
        }
        return result;
    }

    public String check(String src) {
        src = src + " ";
        char[] reader = src.toCharArray();
        StringBuilder writer = new StringBuilder();
        StringBuilder opentag = new StringBuilder();
        boolean isEmptytag = false, isOpentag = false, isClosetag = false;
        StringBuilder closetag = new StringBuilder();
        StringBuilder attrName = new StringBuilder();
        StringBuilder attrValue = new StringBuilder();
        Map<String, String> attributes = new HashMap<>();

        StringBuilder content = new StringBuilder();
        Stack<String> stack = new Stack<>();

        String state = CONTENT;

        for (int i = 0; i < reader.length; i++) {
            char c = reader[i];

            switch (state) {
                case CONTENT:
                    if (c == LT) {
                        state = OPEN_BRACKET;
                        writer.append(content.toString()
                                .trim()
                                .replace("&", "&amp;"));
                    } else {
                        content.append(c);
                    }
                    break;

                case OPEN_BRACKET:
                    if (isStartTagChars(c)) {
                        state = OPEN_TAG_NAME;

                        isOpentag = true;
                        isClosetag = false;
                        isEmptytag = false;

                        opentag.setLength(0);
                        opentag.append(c);
                    } else if (c == SLASH) {
                        state = CLOSE_TAG_SLASH;

                        isOpentag = false;
                        isClosetag = true;
                        isEmptytag = false;
                    }
                    break;
                case OPEN_TAG_NAME:
                    if (isTagChars(c)) {
                        opentag.append(c);
                    } else if (isSpace(c)) {
                        state = TAG_INNER;

                        attributes.clear();
                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                    } else if (c == SLASH) {
                        state = EMPTY_SLASH;
                    }
                    break;
                case TAG_INNER:
                    if (isSpace(c)) {
                    } else if (isStartAttrChars(c)) {
                        state = ATTR_NAME;

                        attrName.setLength(0);
                        attrName.append(c);
                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                    } else if (c == SLASH) {
                        state = EMPTY_SLASH;
                    }
                    break;
                case ATTR_NAME:
                    if (isAttrChars(c)) {
                        attrName.append(c);
                    } else if (c == EQ) {
                        state = EQUAL;
                    } else if (isSpace(c)) {
                        state = EQUAL_WAIT;
                    } else { //Exception
                        if (c == SLASH) {
                            attributes.put(attrName.toString(), "true");
                            state = EMPTY_SLASH;
                        } else if (c == GT) {
                            attributes.put(attrName.toString(), "true");
                            state = CLOSE_BRACKET;
                        }
                    }
                    break;
                case EQUAL_WAIT:
                    if (isSpace(c)) {
                        //loop
                    } else if (c == EQ) {
                        state = EQUAL;
                    } else {
                        //Exception
                        if (isStartAttrChars(c)) {
                            attributes.put(attrName.toString(), "true");
                            state = ATTR_NAME;

                            attrName.setLength(0);
                            attrName.append(c);
                        }
                    }
                    break;
                case CLOSE_TAG_NAME:
                    if (isTagChars(c)) {
                        closetag.append(c);
                    } else if (isSpace(c)) {
                        state = WAIT_END_TAG_CLOSE;
                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                    }
                    break;

                case EQUAL:
                    if (isSpace(c)) {

                    } else if (c == D_QUOT || c == S_QUOT) {
                        quote = c;
                        state = ATTR_VALUE_Q;

                        attrValue.setLength(0);
                    } else if (!isSpace(c) && c != GT) {
                        state = ATTR_VALUE_NQ;

                        attrValue.setLength(0);
                        attrValue.append(c);

                    }
                    break;
                case ATTR_VALUE_Q:
                    if (c != quote) {
                        attrValue.append(c);
                    } else if (c == quote) {
                        state = TAG_INNER;
                        attributes.put(attrName.toString(), attrValue.toString());
                    }
                    break;

                case ATTR_VALUE_NQ:
                    if (!isSpace(c) && c != GT) {
                        attrValue.append(c);
                    } else if (isSpace(c)) {
                        state = TAG_INNER;
                        attributes.put(attrName.toString(), attrValue.toString());
                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                        attributes.put(attrName.toString(), attrValue.toString());
                    }
                    break;
                case EMPTY_SLASH:
                    if (c == GT) {
                        state = CLOSE_BRACKET;
                        isEmptytag = true;
                    }
                    break;
                case CLOSE_TAG_SLASH:
                    if (isStartTagChars(c)) {
                        state = CLOSE_TAG_NAME;

                        closetag.setLength(0);
                        closetag.append(c);
                    }
                    break;
                case WAIT_END_TAG_CLOSE:
                    if (isSpace(c)) {

                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                    }
                    break;
                case CLOSE_BRACKET:
                    if (isOpentag) {
                        String openTagName = opentag.toString().toLowerCase();

                        if (INLINE_TAGS.contains(openTagName)) {
                            isEmptytag = true;
                        }
                        writer.append(LT)
                                .append(openTagName)
                                .append(conver(attributes))
                                .append((isEmptytag ? "/" : ""))
                                .append(GT);

                        attributes.clear();

                        //STACK HERE : push open-tag
                        if (!isEmptytag) {
                            stack.push(openTagName);
                        }
                    } else if (isClosetag) {
                        String closeTagName = closetag.toString().toLowerCase();

                        if (!stack.isEmpty() && stack.contains(closeTagName)) {
                            while (!stack.isEmpty() && !stack.peek().equals(closeTagName)) {
                                writer.append(LT)
                                        .append(SLASH)
                                        .append(stack.pop())
                                        .append(GT);
                            }
                            if (!stack.isEmpty() && stack.peek().equals(closeTagName)) {
                                writer.append(LT)
                                        .append(SLASH)
                                        .append(stack.pop())
                                        .append(GT);
                            }
                        }// end close-tag missing
                    }
                    if (c == LT) {
                        state = OPEN_BRACKET;
                    } else {
                        state = CONTENT;

                        content.setLength(0);
                        content.append(c);
                    }
                    break;

            }//end switch

        }//end for reader

        if (CONTENT.equals(state)) {
            writer.append(content.toString().trim().replace("&", "&amp;"));
        }

        //pop out all left tags
        while (!stack.isEmpty()) {
            writer.append(LT)
                    .append(SLASH)
                    .append(stack.pop())
                    .append(GT);
        }
        return writer.toString();
    }

}