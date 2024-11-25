package gg.wellplayed.backend.dataTransfer.tag;



import gg.wellplayed.backend.model.Tag;

public record TagCreateDTO(
	String name ,
	String description )
	
{
	public Tag parseToTagEntity() {
		Tag t  =new Tag();
		t.setName(this.name());
		t.setDescription(this.description);
		return t;
	}
}
