package mirrg.boron.peg.ilmenite.objects;

import java.util.function.Consumer;

import mirrg.boron.peg.ilmenite.IlmeniteRuntimeException;

public abstract class ObjectFunction extends ObjectScalar
{

	public abstract ObjectValue apply(ObjectVector arguments) throws IlmeniteRuntimeException;

	@Override
	public void stringify(Consumer<String> stringBuilder)
	{
		stringBuilder.accept("CODE");
	}

}
