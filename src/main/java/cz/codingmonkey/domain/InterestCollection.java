package cz.codingmonkey.domain;

import lombok.NonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rstefanca
 */
class InterestCollection {

	private final List<Interest> interests;

	InterestCollection(@NonNull List<Interest> interests) {
		this.interests = new ArrayList<>(interests);
	}

	BigDecimal sum() {
		BigDecimal sum = BigDecimal.ZERO;

		for (Interest interest : interests) {
			sum = sum.add(interest.getRealValue().getAmount());
		}

		return sum;
	}
}
