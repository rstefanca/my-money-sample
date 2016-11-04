package cz.codingmonkey.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author rstefanca
 */
class InterestCollection {

	private final List<Interest> interests;

	InterestCollection(List<Interest> interests) {
		Objects.requireNonNull(interests, "interests");
		this.interests = new ArrayList<>(interests);
	}

	BigDecimal sum() {
		BigDecimal sum = BigDecimal.ZERO;

		for (Interest interest : interests) {
			sum = sum.add(interest.getValue());
		}

		return sum;
	}
}
