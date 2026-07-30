package gg.wellplayed.backend.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import gg.wellplayed.backend.dataTransfer.api.ApiResponse;
import gg.wellplayed.backend.dataTransfer.game.GameCreateDTO;
import gg.wellplayed.backend.dataTransfer.game.GamePatchDTO;
import gg.wellplayed.backend.dataTransfer.game.LinkPlatformDTO;
import gg.wellplayed.backend.dataTransfer.game.LinkShopDTO;
import gg.wellplayed.backend.dataTransfer.game.LinkStudioDTO;
import gg.wellplayed.backend.model.Game;
import gg.wellplayed.backend.model.Platform;
import gg.wellplayed.backend.model.Shop;
import gg.wellplayed.backend.model.Studio;
import gg.wellplayed.backend.model.Tag;
import gg.wellplayed.backend.service.FileStorageService;
import gg.wellplayed.backend.service.FranchiseService;
import gg.wellplayed.backend.service.GameService;
import gg.wellplayed.backend.service.ShopService;
import gg.wellplayed.backend.service.StudioService;
import gg.wellplayed.backend.service.PlatformService;
import gg.wellplayed.backend.service.TagService;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "*", methods = {org.springframework.web.bind.annotation.RequestMethod.GET,
	org.springframework.web.bind.annotation.RequestMethod.POST,
	org.springframework.web.bind.annotation.RequestMethod.PUT,
	org.springframework.web.bind.annotation.RequestMethod.PATCH,
	org.springframework.web.bind.annotation.RequestMethod.DELETE})
public class GameController {
	@Autowired
	GameService gameService;
	@Autowired
	ShopService shopService;
	@Autowired
	StudioService studioService;
	@Autowired
	PlatformService platformService;
	@Autowired
	TagService tagService;
	@Autowired
	FileStorageService fileStorageService;
	@Autowired
	FranchiseService franchiseService;


	/*  CRUD operations  */
	
	@GetMapping
	public ApiResponse listGames() {
		List<Game> games = gameService.findAll();
		String msj = String.format("Total: %d games", games.size());

		return new ApiResponse(msj, games);
	}
	
	@GetMapping("/{id}")
	public ApiResponse getGame(@PathVariable("id") Long id) {
		
		Game game = gameService.getOne(id);
		String title = game.getTitle();
		System.out.println(game);
		return new ApiResponse(
			title,
			game, 
			HttpStatus.OK);
	}

	@PostMapping
	 public ApiResponse makeGame(@RequestBody GameCreateDTO gameReq) {
	  try {
		  Game game = gameReq.parseToGameEntity();

		  if (gameReq.franchise() != null && gameReq.franchise() > 0) {
			  game.setFranchise(franchiseService.getOne(gameReq.franchise()));
		  }
		  if (gameReq.tags() != null) {
			  game.setTags(gameReq.tags().stream()
				  .filter(tagId -> tagId != null && tagId > 0)
				  .map(tagId -> tagService.getOne(tagId))
				  .collect(java.util.stream.Collectors.toList()));
		  }
		  if (gameReq.studios() != null) {
			  game.setStudios(gameReq.studios().stream()
				  .filter(studioId -> studioId != null && studioId > 0)
				  .map(studioId -> studioService.getOne(studioId))
				  .collect(java.util.stream.Collectors.toList()));
		  }
		  if (gameReq.shops() != null) {
			  game.setShops(gameReq.shops().stream()
				  .filter(shopId -> shopId != null && shopId > 0)
				  .map(shopId -> shopService.getOne(shopId))
				  .collect(java.util.stream.Collectors.toList()));
		  }
		  if (gameReq.platforms() != null) {
			  game.setPlatforms(gameReq.platforms().stream()
				  .filter(platformId -> platformId != null && platformId > 0)
				  .map(platformId -> platformService.getOne(platformId))
				  .collect(java.util.stream.Collectors.toList()));
		  }

		  Game saved = gameService.saveUser(game);
		  return new ApiResponse("Game created successfully", saved, HttpStatus.CREATED);
	  }
	  catch (Exception e) {
		  return new ApiResponse("Error creating game: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse deleteGame(@PathVariable("id") Long id) {
		gameService.deleteById(id);
		return new ApiResponse("Game  deleted");
	}

	@PatchMapping("/{id}")
	public ApiResponse patch(@PathVariable("id") Long id, @RequestBody GamePatchDTO gameReq) {
		try {
			Game game = gameService.getOne(id);

			// Update basic fields if provided
			if (gameReq.title() != null) {
				game.setTitle(gameReq.title());
			}
			if (gameReq.synopsis() != null) {
				game.setSynopsis(gameReq.synopsis());
			}
			if (gameReq.releaseDate() != null) {
				game.setReleaseDate(gameReq.releaseDate());
			}
			if (gameReq.portrait() != null) {
				game.setPortrait(gameReq.portrait());
			}
			if (gameReq.banner() != null) {
				game.setBanner(gameReq.banner());
			}
			if (gameReq.pictures() != null) {
				game.setPictures(gameReq.pictures());
			}

			if (gameReq.franchise() != null) {
				if (gameReq.franchise() > 0) {
					game.setFranchise(franchiseService.getOne(gameReq.franchise()));
				} else {
					game.setFranchise(null);
				}
			}

			// Update tags relationship
			if (gameReq.tags() != null) {
				List<Tag> tags = gameReq.tags().stream()
					.filter(tagId -> tagId != null && tagId > 0)
					.map(tagId -> tagService.getOne(tagId))
					.collect(java.util.stream.Collectors.toList());
				game.setTags(tags);
			}

			// Update studios relationship
			if (gameReq.studios() != null) {
				List<Studio> studios = gameReq.studios().stream()
					.filter(studioId -> studioId != null && studioId > 0)
					.map(studioId -> studioService.getOne(studioId))
					.collect(java.util.stream.Collectors.toList());
				game.setStudios(studios);
			}

			// Update shops relationship
			if (gameReq.shops() != null) {
				List<Shop> shops = gameReq.shops().stream()
					.filter(shopId -> shopId != null && shopId > 0)
					.map(shopId -> shopService.getOne(shopId))
					.collect(java.util.stream.Collectors.toList());
				game.setShops(shops);
			}

			// Update platforms relationship
			if (gameReq.platforms() != null) {
				List<Platform> platforms = gameReq.platforms().stream()
					.filter(platformId -> platformId != null && platformId > 0)
					.map(platformId -> platformService.getOne(platformId))
					.collect(java.util.stream.Collectors.toList());
				game.setPlatforms(platforms);
			}

			Game updated = gameService.saveUser(game);
			return new ApiResponse("Game updated successfully", updated);
		}
		catch (Exception e) {
			return new ApiResponse("Error updating game: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*  Search & filter  */

	@GetMapping("/search")
	public ApiResponse search(@RequestParam("title") String title) {
		return new ApiResponse(gameService.findByTitle(title));
	}

	@GetMapping("/filter")
	public ApiResponse filter(
			@RequestParam(value = "tags", required = false) List<Long> tags,
			@RequestParam(value = "platform", required = false) List<Long> platform,
			@RequestParam(value = "studio", required = false) List<Long> studio,
			@RequestParam(value = "franchise", required = false) List<Long> franchise,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
			@RequestParam(value = "minStarValue", required = false) Float minStarValue,
			@RequestParam(value = "maxStarValue", required = false) Float maxStarValue) {
		List<Game> games = gameService.filterGames(
				tags, platform, studio, franchise,
				startDate != null ? startDate.toLocalDate() : null,
				endDate != null ? endDate.toLocalDate() : null,
				minStarValue, maxStarValue);
		return new ApiResponse(games);
	}

	/*  Uploads  */

	@PatchMapping("/{id}/uploads/portrait")
	public ApiResponse uploadPortrait(@PathVariable("id") Long id, @RequestParam("portrait") MultipartFile file) {
		Game game = gameService.getOne(id);
		String filename = fileStorageService.store(file);
		game.setPortrait(filename);
		Game updated = gameService.saveUser(game);
		return new ApiResponse("Portrait actualizado", updated);
	}

	@PatchMapping("/{id}/uploads/banner")
	public ApiResponse uploadBanner(@PathVariable("id") Long id, @RequestParam("banner") MultipartFile file) {
		Game game = gameService.getOne(id);
		String filename = fileStorageService.store(file);
		game.setBanner(filename);
		Game updated = gameService.saveUser(game);
		return new ApiResponse("Banner actualizado", updated);
	}

	/*  Relationship operations	 */

	@PostMapping("/{id}/shops")
	public ApiResponse linkShop(@PathVariable("id") Long gameId, @RequestBody LinkShopDTO linkShopReq) {
		Game game = gameService.getOne(gameId);
		Shop shop = shopService.getOne(linkShopReq.shopId());
		game.linkShop(shop);
		gameService.saveUser(game);
		return new ApiResponse("Dang, they really sell this there? (Game associated to shop and updated)");
	}
	
	@PostMapping("/{id}/studios")
	public ApiResponse linkStudio(@PathVariable("id") Long gameId, @RequestBody LinkStudioDTO linkStudioReq) {
		Game game = gameService.getOne(gameId);
		Studio studio = studioService.getOne(linkStudioReq.studioId());
		game.linkStudio(studio);
		gameService.saveUser(game);
		return new ApiResponse("Studio linked correctly");
	}
	
	@PostMapping("/{id}/platforms")
	public ApiResponse linkPlatform(@PathVariable("id") Long gameId, @RequestBody LinkPlatformDTO linkPlatformReq) {
		Game game = gameService.getOne(gameId);
		Platform platform = platformService.getOne(linkPlatformReq.platformId());
		game.linkPlatform(platform);
		gameService.saveUser(game);
		return new ApiResponse("Platform linked correctly");
	}
	
}
