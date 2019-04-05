package mirrg.boron.peg.syntaxes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mirrg.boron.peg.core.Memo;
import mirrg.boron.peg.core.Node;
import mirrg.boron.peg.core.Syntax;

public class SyntaxRegex extends Syntax<Matcher>
{

	public final Pattern pattern;

	public SyntaxRegex(String regex)
	{
		this.pattern = Pattern.compile(regex);
	}

	@Override
	protected Node<Matcher> parseImpl(Memo memo, boolean shouldTokenProposal, String text, int index)
	{
		Matcher matcher = pattern.matcher(text);
		if (matcher.find(index)) {
			if (matcher.start() == index) {
				return new Node<>(this, null, index, matcher.end(), matcher);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public String getName()
	{
		String name = super.getName();
		if (name != null) return name;
		return pattern.pattern();
	}

}
