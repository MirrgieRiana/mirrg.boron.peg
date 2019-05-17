package mirrg.boron.peg.ilmenite.objects;

import java.util.function.Consumer;

public abstract class ObjectValue
{

	ObjectValue()
	{

	}

	public abstract void stringify(Consumer<String> stringBuilder);

	public String stringify()
	{
		StringBuilder sb = new StringBuilder();
		stringify(sb::append);
		return sb.toString();
	}

	@Override
	public String toString()
	{
		return stringify();
	}

}
