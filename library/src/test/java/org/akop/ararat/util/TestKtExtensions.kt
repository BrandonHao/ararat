// Copyright (c) Akop Karapetyan
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package org.akop.ararat.util

import org.akop.ararat.io.FormatException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test


class TestKtExtensions {

    private val JS_ENCODED_STRING = """
        %uFC04%00%u3C66%u5416%u8350%u4053%u01A6%uE170
        %u7D9F%u60B1%u5480%u0E82%uC001%u98D4%uFE83%u07D8
        %26%u2F83%uE7D8%uB05E%u35C6%u4DAA%u3C4A%u8713
        %u8C0F%u8000%u27B2V%u0F8D%uE352""".trimIndent()
    private val JAVA_ENCODED_STRING = """
        \ufc04\u0000\u3c66\u5416\u8350\u4053\u01a6\ue170
        \u7d9f\u60b1\u5480\u0e82\uc001\u98d4\ufe83\u07d8
        &\u2f83\ue7d8\ub05e\u35c6\u4daa\u3c4a\u8713
        \u8c0f\u8000\u27b2V\u0f8d\ue352""".trimIndent()
    private val VALID_DECODED_STRING = """
        ﰄ 㱦吖荐䁓Ʀ
        綟悱咀ຂ쀁飔ﺃߘ
        &⾃끞㗆䶪㱊蜓
        谏耀➲Vྍ""".trimIndent()
    private val INVALID_ENCODED_STRING = """
        %uFC04%00%u3C66%u5416%u8350%u4053%u01A6%uE170
        %u7D9F%u60B1%u5480%u0E82%uC001%u98D4%uFE83%u07D8
        %26%u2F83%uE7D8%uB05E%u35C6%u4DAA%u3C4A%u8713
        %u8C0F%u8000%u27B2V%u0F8D%uE352%""".trimIndent()

    @Test
    fun decodeJsUnicode_givenBadlyEncodedString_ensureThrows() {
        assertThrows(FormatException::class.java) {
            INVALID_ENCODED_STRING.decodeJsUnicode()
        }
    }

    @Test
    fun decodeJsUnicode_givenWellEncodedString_ensureDecodes() {
        assertEquals(VALID_DECODED_STRING,
                JS_ENCODED_STRING.decodeJsUnicode())
    }

    @Test
    fun encodeUnicode_givenString_ensureJavaEncodes() {
        assertEquals(JAVA_ENCODED_STRING,
                VALID_DECODED_STRING.encodeUnicode())
    }
}
