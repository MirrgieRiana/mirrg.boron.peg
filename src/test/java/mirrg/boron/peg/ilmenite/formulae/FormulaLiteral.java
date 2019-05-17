package mirrg.boron.peg.ilmenite.formulae;

import java.util.function.Consumer;

import mirrg.boron.peg.ilmenite.Context;
import mirrg.boron.peg.ilmenite.IlmeniteRuntimeException;
import mirrg.boron.peg.ilmenite.objects.ObjectValue;

public class FormulaLiteral extends FormulaBase
{

	public final ObjectValue value;

	public FormulaLiteral(ObjectValue value)
	{
		this.value = value;
	}

	@Override
	public ObjectValue eval(Context context) throws IlmeniteRuntimeException
	{
		return value;
	}

	@Override
	public void stringify(Consumer<String> stringBuilder)
	{
		stringBuilder.accept("LITERAL[");
		value.stringify(stringBuilder);
		stringBuilder.accept("]");
	}

}
