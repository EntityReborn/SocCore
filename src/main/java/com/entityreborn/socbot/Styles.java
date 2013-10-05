/*
 * The MIT License
 *
 * Copyright 2013 Jason Unger <entityreborn@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.entityreborn.socbot;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public enum Styles {
    BOLD("\u0002"),
    ITALIC("\u0009"),
    RESET("\u000f"),
    STRIKETHRU("\0013"),
    REVERSE("\u0016"),
    UNDERLINE("\u001f");

    String value;

    private Styles(String s) {
        value = s;
    }

    @Override
    public String toString() {
        return value;
    }

    public static String removeAll(String line) {
        line = line.replaceAll("\u0002", "");
        line = line.replaceAll("\u0009", "");
        line = line.replaceAll("\u000f", "");
        line = line.replaceAll("\u0013", "");
        line = line.replaceAll("\u0015", ""); // Alternate underline.
        line = line.replaceAll("\u0016", "");
        line = line.replaceAll("\u001f", "");

        return line;
    }

}
