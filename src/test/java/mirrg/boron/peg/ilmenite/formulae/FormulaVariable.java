package mirrg.boron.peg.ilmenite.formulae;

import java.util.Optional;
import java.util.function.Consumer;

import mirrg.boron.peg.ilmenite.Context;
import mirrg.boron.peg.ilmenite.IlmeniteRuntimeException;
import mirrg.boron.peg.ilmenite.Variable;
import mirrg.boron.peg.ilmenite.objects.ObjectValue;

public class FormulaVariable extends FormulaBase
{

	public final String identifier;

	public FormulaVariable(String identifier)
	{
		this.identifier = identifier;
	}

	@Override
	public ObjectValue eval(Context context) throws IlmeniteRuntimeException
	{
		Optional<Variable> oVariable = context.stackFrame.getVariable(identifier);
		if (oVariable.isPresent()) {
			return oVariable.get().value;
		} else {
			throw new IlmeniteRuntimeException("No such variable: " + identifier);
		}
	}

	@Override
	public void stringify(Consumer<String> stringBuilder)
	{
		stringBuilder.accept("VARIABLE[");
		stringBuilder.accept(identifier);
		stringBuilder.accept("]");
	}

}
