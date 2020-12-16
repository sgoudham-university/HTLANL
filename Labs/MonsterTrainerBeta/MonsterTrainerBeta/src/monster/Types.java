package monster;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Types {
	FIRE("FIRE", "WATER"),
	WATER("WATER", "GRASS"),
	ELECTRIC("ELECTRIC", "GRASS"),
	GRASS("FIRE", "GRASS");
	
	private Set<String> weaknesses;
	
	Types(String... weakStr) {
		weaknesses = Arrays.stream(weakStr).collect(Collectors.toSet());
	}
	
	public boolean isWeakAgainst(Types types) {
		return weaknesses.contains(types.name());
	}
}
