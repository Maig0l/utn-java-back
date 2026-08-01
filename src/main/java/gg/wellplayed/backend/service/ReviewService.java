package gg.wellplayed.backend.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gg.wellplayed.backend.dataTransfer.review.ReviewPatchDTO;
import gg.wellplayed.backend.model.Review;
import gg.wellplayed.backend.repository.ReviewRepository;


@Service
public class ReviewService {
	@Autowired
	ReviewRepository reviewRepository;
	
	public Review saveReview(Review review) {
		return reviewRepository.save(review);
	}
	
	public List<Review> findAll() {
		return reviewRepository.findAll();
	}
	
	public Review getOne(Long id) {
		return reviewRepository.findById(id).orElseThrow();
	}

	public List<Review> findByAuthorNick(String nick) {
		return reviewRepository.findByAuthor_Nick(nick);
	}
	
	public Review update(Long id, Review newReview) {
		Review s = getOne(id);
		s.setTitle(newReview.getTitle());
		s.setBody(newReview.getBody());
		
		return reviewRepository.save(s);
	}

	public Review patch(Long id, ReviewPatchDTO reviewPatch) {
		Review s = getOne(id);

		if (reviewPatch.title() != null) {
			s.setTitle(reviewPatch.title());
		}
		if (reviewPatch.body() != null) {
			s.setBody(reviewPatch.body());
		}
		if (reviewPatch.score() != null) {
			s.setScore(reviewPatch.score());
		}

		return reviewRepository.save(s);
	}

	public Review delete(Long id) {
		Review s = getOne(id);
		Review ret = new Review();
		reviewRepository.delete(s);
		return ret;
	}
}
