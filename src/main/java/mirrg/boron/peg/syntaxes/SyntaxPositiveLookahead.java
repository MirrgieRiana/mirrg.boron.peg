package mirrg.boron.peg.syntaxes;

import mirrg.boron.peg.core.Memo;
import mirrg.boron.peg.core.Node;
import mirrg.boron.peg.core.Syntax;

public class SyntaxPositiveLookahead<T> extends Syntax<T>
{

	public final Syntax<T> syntax;

	public SyntaxPositiveLookahead(Syntax<T> syntax)
	{
		this.syntax = syntax;
	}

	@Override
	protected Node<T> parseImpl(Memo memo, boolean shouldTokenProposal, String text, int index)
	{
		Node<T> node = syntax.parse(memo, shouldTokenProposal, text, index);
		if (node == null) return null;
		return new Node<>(this, null, index, index, null);
	}

}
