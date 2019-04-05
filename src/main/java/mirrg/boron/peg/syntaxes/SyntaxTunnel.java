package mirrg.boron.peg.syntaxes;

import static mirrg.boron.peg.HSyntaxOxygen.*;

import java.util.function.Supplier;

import mirrg.boron.peg.core.Memo;
import mirrg.boron.peg.core.Node;
import mirrg.boron.peg.core.Syntax;

public class SyntaxTunnel<T> extends Syntax<Supplier<T>>
{

	public final SyntaxExtract<Supplier<T>> syntaxExtract = new SyntaxExtract<>();

	@Override
	protected Node<Supplier<T>> parseImpl(Memo memo, boolean shouldTokenProposal, String text, int index)
	{
		return syntaxExtract.parse(memo, shouldTokenProposal, text, index);
	}

	public SyntaxTunnel<T> and(Syntax<?> syntax)
	{
		syntaxExtract.and(syntax);
		return this;
	}

	public SyntaxTunnel<T> extract(Syntax<T> syntax)
	{
		syntaxExtract.extract(pack(syntax, t -> () -> t));
		return this;
	}

}
