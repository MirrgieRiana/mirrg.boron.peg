package mirrg.boron.peg.syntaxes;

import static mirrg.boron.peg.UtilsSyntax.*;

import java.util.ArrayList;

import mirrg.boron.peg.core.Memo;
import mirrg.boron.peg.core.Node;
import mirrg.boron.peg.core.Syntax;
import mirrg.boron.util.struct.ImmutableArray;
import mirrg.boron.util.struct.Struct2;

public class SyntaxOperation<OPERAND, OPERATOR> extends Syntax<Operation<OPERAND, OPERATOR>>
{

	public final Syntax<Operation<OPERAND, OPERATOR>> syntax;

	public SyntaxOperation(Syntax<OPERAND> syntaxOperand, Syntax<OPERATOR> syntaxOperator)
	{
		this.syntax = pack(serial(Struct2<OPERAND, ArrayList<Struct2<OPERATOR, OPERAND>>>::new)
			.and(syntaxOperand, Struct2::setX)
			.and(repeat(serial(Struct2<OPERATOR, OPERAND>::new)
				.and(syntaxOperator, Struct2::setX)
				.and(syntaxOperand, Struct2::setY)), Struct2::setY),
			t -> {
				ArrayList<OPERAND> operands = new ArrayList<>();
				ArrayList<OPERATOR> operators = new ArrayList<>();
				operands.add(t.x);
				for (int i = 0; i < t.y.size(); i++) {
					operators.add(t.y.get(i).x);
					operands.add(t.y.get(i).y);
				}
				return new Operation<>(ImmutableArray.ofList(operands), ImmutableArray.ofList(operators));
			});
	}

	@Override
	protected Node<Operation<OPERAND, OPERATOR>> parseImpl(Memo memo, boolean shouldTokenProposal, String text, int index)
	{
		return syntax.parse(memo, shouldTokenProposal, text, index);
	}

}
