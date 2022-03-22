package com.bogdan.mochacasestudy.q2q3

/**
 * Create a function in Kotlin that takes such a string and returns an SpannableString string with the characters
 * between "@" in bold, those between "$" in italics and removing those between "#" (can't remember what they represent,
 * so let’s just remove them).
 */

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import java.util.regex.Pattern


const val testGitText =
    "@My Very First Commit@ \$by AdiR\$ #s. f.# (#Med.#) This fixes most important bugs #2020-10-10# @Weather App@"

enum class SpanPattern(val value: String) {
    DELETE("\\#.*?\\#"),
    BOLD("\\@.*?\\@"),
    ITALIC("\\$.*?\\$")
}

fun decodeGitMessage(input: String): SpannableStringBuilder {
    val spannable = SpannableStringBuilder()
    val deletePattern = Pattern.compile(SpanPattern.DELETE.value)
    val newInput = deletePattern.matcher(input).replaceAll("")

    val boldPattern = Pattern.compile(SpanPattern.BOLD.value)
    val bigMatcher =
        Pattern.compile("${SpanPattern.BOLD.value}|${SpanPattern.ITALIC.value}").matcher(newInput)

    val sb = StringBuffer()
    while (bigMatcher.find()) {
        sb.setLength(0)
        val group = bigMatcher.group()
        val str = group.substring(1, group.length - 1)
        bigMatcher.appendReplacement(sb, str)
        spannable.append(sb.toString())
        val start: Int = spannable.length - str.length
        val typeface = if (boldPattern.matcher(group).matches()) {
            Typeface.BOLD
        } else {
            Typeface.ITALIC
        }
        spannable.setSpan(
            StyleSpan(typeface),
            start,
            spannable.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    return spannable
}
