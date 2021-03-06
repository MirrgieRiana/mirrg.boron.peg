package mirrg.boron.peg.syntaxes;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import mirrg.boron.peg.UtilsSyntax;
import mirrg.boron.peg.core.Memo;
import mirrg.boron.peg.core.Node;
import mirrg.boron.peg.core.Syntax;

public class SyntaxSerial<T> extends Syntax<T>
{

	public final Supplier<T> supplier;
	public final ArrayList<Function<T, Syntax<?>>> syntaxes = new ArrayList<>();

	public SyntaxSerial(Supplier<T> supplier)
	{
		this.supplier = supplier;
	}

	public SyntaxSerial<T> and(Syntax<?> syntax)
	{
		syntaxes.add(t -> syntax);
		return this;
	}

	@SuppressWarnings("deprecation")
	public <T2> SyntaxSerial<T> and(Syntax<T2> syntax, BiConsumer<T, T2> function)
	{
		syntaxes.add(t -> UtilsSyntax.map(syntax, t2 -> {
			function.accept(t, t2);
			return t2;
		}));
		return this;
	}

	public SyntaxSerial<T> and(Function<T, Syntax<?>> function)
	{
		syntaxes.add(function);
		return this;
	}

	@SuppressWarnings("deprecation")
	public <T2> SyntaxSerial<T> and(Function<T, Syntax<T2>> function, BiConsumer<T, T2> function2)
	{
		syntaxes.add(t -> UtilsSyntax.map(function.apply(t), t2 -> {
			function2.accept(t, t2);
			return t2;
		}));
		return this;
	}

	@Override
	protected Node<T> parseImpl(Memo memo, boolean shouldTokenProposal, String text, int index)
	{
		T t = supplier.get();
		ArrayList<Node<?>> children = new ArrayList<>();
		int begin = index;
		int end = begin;

		for (Function<T, Syntax<?>> syntax : syntaxes) {
			Node<?> node = syntax.apply(t).parse(memo, shouldTokenProposal, text, index);
			if (node == null) return null;
			children.add(node);
			index += node.end - node.begin;
			end = node.end;
		}

		return new Node<>(this, children, begin, end, t);
	}

}
