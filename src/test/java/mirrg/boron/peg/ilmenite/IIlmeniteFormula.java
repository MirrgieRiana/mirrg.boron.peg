package mirrg.boron.peg.ilmenite;

import java.util.function.Consumer;

import mirrg.boron.peg.ilmenite.objects.ObjectValue;

public interface IIlmeniteFormula
{

	public ObjectValue eval(Context context) throws IlmeniteRuntimeException;

	public abstract void stringify(Consumer<String> stringBuilder);

	public default String stringify()
	{
		StringBuilder sb = new StringBuilder();
		stringify(sb::append);
		return sb.toString();
	}

}
