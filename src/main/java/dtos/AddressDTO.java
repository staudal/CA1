package dtos;

import entities.Address;
import entities.Hobby;
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
public class AddressDTO {

    private String street;
    private String additionalInfo;

    public static List<AddressDTO> getDTOs(List<Address> addresses){
        List<AddressDTO> addressDTOs = new ArrayList();
        addresses.forEach(address -> addressDTOs.add(new AddressDTO(address)));
        return addressDTOs;
    }

    public AddressDTO(Address address) {
        this.street = address.getStreet();
        this.additionalInfo = address.getAdditionalInfo();
    }

}
