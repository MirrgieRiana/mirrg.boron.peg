package mirrg.boron.peg.ilmenite.formulae;

import mirrg.boron.peg.ilmenite.IIlmeniteFormula;

public abstract class FormulaBase implements IIlmeniteFormula
{

	@Override
	public String toString()
	{
		return stringify();
	}

}
