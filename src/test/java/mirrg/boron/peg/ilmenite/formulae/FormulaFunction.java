package mirrg.boron.peg.ilmenite.formulae;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import mirrg.boron.peg.ilmenite.Context;
import mirrg.boron.peg.ilmenite.IIlmeniteFormula;
import mirrg.boron.peg.ilmenite.IlmeniteRuntimeException;
import mirrg.boron.peg.ilmenite.IlmeniteScript;
import mirrg.boron.peg.ilmenite.objects.ObjectFunction;
import mirrg.boron.peg.ilmenite.objects.ObjectValue;
import mirrg.boron.peg.ilmenite.objects.ObjectVector;

public class FormulaFunction extends FormulaBase
{

	public final IIlmeniteFormula formulaFunction;
	public final IIlmeniteFormula[] formulasArguments;

	public FormulaFunction(IIlmeniteFormula formulaFunction, IIlmeniteFormula... formulasArguments)
	{
		this.formulaFunction = formulaFunction;
		this.formulasArguments = formulasArguments;
	}

	@Override
	public ObjectValue eval(Context context) throws IlmeniteRuntimeException
	{
		ObjectFunction function = IlmeniteScript.cast(ObjectFunction.class, formulaFunction.eval(context));

		ObjectVector vector;
		if (formulasArguments.length == 0) {
			vector = ObjectVector.EMPTY;
		} else if (formulasArguments.length == 1) {
			vector = ObjectVector.pack(formulasArguments[0].eval(context));
		} else {
			List<ObjectValue> values = new ArrayList<>();
			for (IIlmeniteFormula formula : formulasArguments) {
				values.add(formula.eval(context));
			}
			vector = IlmeniteScript.vector(values);
		}

		return function.apply(vector);
	}

	@Override
	public void stringify(Consumer<String> stringBuilder)
	{
		stringBuilder.accept("FUNCTION[");
		formulaFunction.stringify(stringBuilder);
		stringBuilder.accept(",");
		boolean first = true;
		for (IIlmeniteFormula formula : formulasArguments) {
			if (first) {
				first = false;
			} else {
				stringBuilder.accept(",");
			}
			formula.stringify(stringBuilder);
		}
		stringBuilder.accept("]");
	}

}
