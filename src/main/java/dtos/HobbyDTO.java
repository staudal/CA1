package dtos;

import entities.Hobby;
import entities.Phone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HobbyDTO {

    private String name;
    private String description;

    public static List<HobbyDTO> getDTOs(List<Hobby> hobbies){
        List<HobbyDTO> hobbyDTOs = new ArrayList();
        hobbies.forEach(hobby -> hobbyDTOs.add(new HobbyDTO(hobby)));
        return hobbyDTOs;
    }

    public HobbyDTO(Hobby hobby) {
        this.name = hobby.getName();
        this.description = hobby.getDescription();
    }

}
