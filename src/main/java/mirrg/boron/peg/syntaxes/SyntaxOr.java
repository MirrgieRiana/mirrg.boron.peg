package mirrg.boron.peg.syntaxes;

import java.util.ArrayList;

import mirrg.boron.peg.core.Memo;
import mirrg.boron.peg.core.Node;
import mirrg.boron.peg.core.Syntax;

public class SyntaxOr<T> extends Syntax<T>
{

	public final ArrayList<Syntax<T>> syntaxex = new ArrayList<>();

	public SyntaxOr<T> or(Syntax<T> syntax)
	{
		syntaxex.add(syntax);
		return this;
	}

	@Override
	protected Node<T> parseImpl(Memo memo, boolean shouldTokenProposal, String text, int index)
	{
		for (Syntax<T> syntax : syntaxex) {
			Node<T> node = syntax.parse(memo, shouldTokenProposal, text, index);
			if (node != null) return node;
		}
		return null;
	}

}
