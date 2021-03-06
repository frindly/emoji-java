package com.vdurmont.emoji;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class EmojiParserTest {
	@Test
	public void parseToAliases_replaces_the_emojis_by_one_of_their_aliases() {
		// GIVEN
		String str = "An 😀awesome 😃string with a few 😉emojis!";

		// WHEN
		String result = EmojiParser.parseToAliases(str);

		// THEN
		assertEquals("An :grinning:awesome :smiley:string with a few :wink:emojis!", result);
	}

	@Test
	public void parseToHtml_replaces_the_emojis_by_their_html_representation() {
		// GIVEN
		String str = "An 😀awesome 😃string with a few 😉emojis!";

		// WHEN
		String result = EmojiParser.parseToHtml(str);

		// THEN
		assertEquals("An &#128512;awesome &#128515;string with a few &#128521;emojis!", result);
	}

	@Test
	public void parseToHtmlHexadecimal_replaces_the_emojis_by_their_htm_hexl_representation() {
		// GIVEN
		String str = "An 😀awesome 😃string with a few 😉emojis!";

		// WHEN
		String result = EmojiParser.parseToHtmlHexadecimal(str);

		// THEN
		assertEquals("An &#x1f600;awesome &#x1f603;string with a few &#x1f609;emojis!", result);
	}

	@Test
	public void parseToUnicode_replaces_the_aliases_and_the_html_by_their_emoji() {
		// GIVEN
		String str = "An :grinning:awesome :smiley:string &#128516;with a few :wink:emojis!";

		// WHEN
		String result = EmojiParser.parseToUnicode(str);

		// THEN
		assertEquals("An 😀awesome 😃string 😄with a few 😉emojis!", result);
	}

	@Test
	public void parseToUnicode_with_the_thumbsup_emoji_replaces_the_alias_by_the_emoji() {
		// GIVEN
		String str = "An :+1:awesome :smiley:string &#128516;with a few :wink:emojis!";

		// WHEN
		String result = EmojiParser.parseToUnicode(str);

		// THEN
		assertEquals("An \uD83D\uDC4Dawesome 😃string 😄with a few 😉emojis!", result);
	}

	@Test
	public void parseToUnicode_with_the_thumbsup_emoji_in_hex_replaces_the_alias_by_the_emoji() {
		// GIVEN
		String str = "An :+1:awesome :smiley:string &#x1f604;with a few :wink:emojis!";

		// WHEN
		String result = EmojiParser.parseToUnicode(str);

		// THEN
		assertEquals("An \uD83D\uDC4Dawesome 😃string 😄with a few 😉emojis!", result);
	}

	@Test
	public void getAliasesCanditates_with_one_alias() {
		// GIVEN
		String str = "test :candidate: test";

		// WHEN
		List<String> candidates = EmojiParser.getAliasesCandidates(str);

		// THEN
		assertEquals(1, candidates.size());
		assertEquals("candidate", candidates.get(0));
	}

	@Test
	public void getAliasesCanditates_with_one_alias_an_another_colon_after() {
		// GIVEN
		String str = "test :candidate: test:";

		// WHEN
		List<String> candidates = EmojiParser.getAliasesCandidates(str);

		// THEN
		assertEquals(1, candidates.size());
		assertEquals("candidate", candidates.get(0));
	}

	@Test
	public void getAliasesCanditates_with_one_alias_an_another_colon_right_after() {
		// GIVEN
		String str = "test :candidate::test";

		// WHEN
		List<String> candidates = EmojiParser.getAliasesCandidates(str);

		// THEN
		assertEquals(1, candidates.size());
		assertEquals("candidate", candidates.get(0));
	}

	@Test
	public void getAliasesCanditates_with_one_alias_an_another_colon_before_after() {
		// GIVEN
		String str = "test ::candidate: test";

		// WHEN
		List<String> candidates = EmojiParser.getAliasesCandidates(str);

		// THEN
		assertEquals(1, candidates.size());
		assertEquals("candidate", candidates.get(0));
	}

	@Test
	public void getAliasesCanditates_with_two_aliases() {
		// GIVEN
		String str = "test :candi: :candidate: test";

		// WHEN
		List<String> candidates = EmojiParser.getAliasesCandidates(str);

		// THEN
		assertEquals(2, candidates.size());
		assertEquals("candi", candidates.get(0));
		assertEquals("candidate", candidates.get(1));
	}

	@Test
	public void getAliasesCanditates_with_two_aliases_sharing_a_colon() {
		// GIVEN
		String str = "test :candi:candidate: test";

		// WHEN
		List<String> candidates = EmojiParser.getAliasesCandidates(str);

		// THEN
		assertEquals(2, candidates.size());
		assertEquals("candi", candidates.get(0));
		assertEquals("candidate", candidates.get(1));
	}
}
