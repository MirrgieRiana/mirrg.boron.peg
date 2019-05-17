package mirrg.boron.peg.ilmenite.formulae;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import mirrg.boron.peg.ilmenite.Context;
import mirrg.boron.peg.ilmenite.IIlmeniteFormula;
import mirrg.boron.peg.ilmenite.IlmeniteRuntimeException;
import mirrg.boron.peg.ilmenite.IlmeniteScript;
import mirrg.boron.peg.ilmenite.objects.ObjectValue;

public class FormulaPack extends FormulaBase
{

	public final IIlmeniteFormula[] formulas;

	public FormulaPack(IIlmeniteFormula... formulas)
	{
		this.formulas = formulas;
	}

	@Override
	public ObjectValue eval(Context context) throws IlmeniteRuntimeException
	{
		List<ObjectValue> values = new ArrayList<>();
		for (IIlmeniteFormula formula : formulas) {
			values.add(formula.eval(context));
		}
		return IlmeniteScript.vector(values);
	}

	@Override
	public void stringify(Consumer<String> stringBuilder)
	{
		stringBuilder.accept("PACK[");
		boolean first = true;
		for (IIlmeniteFormula formula : formulas) {
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
