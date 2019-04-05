package mirrg.boron.peg.syntaxes;

import mirrg.boron.peg.core.Memo;
import mirrg.boron.peg.core.Node;
import mirrg.boron.peg.core.Syntax;

public class SyntaxNamed<T> extends Syntax<T>
{

	public final Syntax<T> syntax;
	public final String name;

	public SyntaxNamed(Syntax<T> syntax, String name)
	{
		this.syntax = syntax;
		this.name = name;
	}

	@Override
	protected Node<T> parseImpl(Memo memo, boolean shouldTokenProposal, String text, int index)
	{
		return syntax.parse(memo, shouldTokenProposal, text, index);
	}

	@Override
	public String getName()
	{
		return name;
	}

}
