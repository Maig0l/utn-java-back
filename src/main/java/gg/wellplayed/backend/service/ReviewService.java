package gg.wellplayed.backend.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gg.wellplayed.backend.model.Review;
import gg.wellplayed.backend.repository.ReviewRepository;


@Service
public class ReviewService {
	@Autowired
	ReviewRepository reviewRepository;
	
	public Review saveUser(Review review) {
		return reviewRepository.save(review);
	}
	
	public List<Review> findAll() {
		return reviewRepository.findAll();
	}
	
	public Review getOne(Long id) {
		Review t = reviewRepository.findById(id).get();
		
		return t;
	}
	
	public Review update(Long id, Review newReview) {
		Review s = getOne(id);
		s.setTitle(newReview.getTitle());
		s.setBody(newReview.getBody());
		
		return reviewRepository.save(s);
	}
	
	public Review delete(Long id) {
		Review s = getOne(id);
		Review ret = new Review();
		reviewRepository.delete(s);
		return ret;
	}
}
