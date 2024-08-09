package io.github.followsclosley.brick.data;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class ColorToDtoTest {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void testDtoToDomain() {
//        ColorDtoV1 dto = new ColorDtoV1("id", "name", "rgb");
//        Color color = modelMapper.map(dto, Color.class);
//
//        assertEquals(dto.getId(), color.getId());
//        assertEquals(dto.getName(), color.getName());
//        assertEquals(dto.getRgb(), color.getRgb());
//        assertNull(color.getParts());
    }

    @Test
    public void testDomainToDto() {
//        Color color = new Color("id", "name", "rgb", "parts");
//        ColorDtoV1 dto = modelMapper.map(color, ColorDtoV1.class);
//
//        assertEquals(color.getId(), dto.getId());
//        assertEquals(color.getName(), dto.getName());
//        assertEquals(color.getRgb(), dto.getRgb());
    }
}