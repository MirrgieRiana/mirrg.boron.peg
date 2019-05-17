package mirrg.boron.peg.ilmenite;

import static org.junit.Assert.*;

import org.junit.Test;

import mirrg.boron.peg.ilmenite.objects.ObjectNumber;
import mirrg.boron.peg.ilmenite.objects.ObjectVector;
import mirrg.boron.util.struct.ImmutableArray;

public class TestIlmeniteValue
{

	@Test
	public void test1()
	{
		assertEquals("34", new ObjectNumber(34, "34").toString());
		assertEquals("[34]", new ObjectVector(ImmutableArray.of(new ObjectNumber(34, "34"))).toString());
	}

}
