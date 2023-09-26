package io.github.followsclosley.brick.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.followsclosley.brick.data.*;
import io.github.followsclosley.brick.data.repository.AssemblageRepository;
import io.github.followsclosley.brick.data.repository.PieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.ArrayList;

@Order(6)
@Component()
@Scope("prototype")
//@ConditionalOnExpression("${catalog.load-on-startup:false}")
public class AssemblageLoader implements ApplicationRunner {

    @Autowired
    private AssemblageRepository assemblageRepository;

    @Autowired
    private PieceRepository pieceRepository;

    public void run(ApplicationArguments args) throws Exception {
        Assemblage shields = new Assemblage();
        shields.setId("shields");
        shields.setName("Classic Shields");

        ArrayList<AssemblagePiece> pieces = new ArrayList();
        //Can not load an entity and then persist it as json for some reason
        //pieces.add(new AssemblagePiece(true, pieceRepository.getReferenceById("3846pb011")));
        pieces.add(new AssemblagePiece(true, new Piece("3846pb014"  , new Element("3846", "Minifigure, Shield Triangular", null), new Color("9","Light Gray","9C9C9C",null))));
        pieces.add(new AssemblagePiece(true, new Piece("3846pb011"  , new Element("3846", "Minifigure, Shield Triangular", null), new Color("9","Light Gray","9C9C9C",null))));
        pieces.add(new AssemblagePiece(true, new Piece("3846pb011eu", new Element("3846", "Minifigure, Shield Triangular", null), new Color("9","Light Gray","9C9C9C",null))));
        pieces.add(new AssemblagePiece(true, new Piece("3846pb012"  , new Element("3846", "Minifigure, Shield Triangular", null), new Color("9","Light Gray","9C9C9C",null))));
        pieces.add(new AssemblagePiece(true, new Piece("3846pb013"  , new Element("3846", "Minifigure, Shield Triangular", null), new Color("9","Light Gray","9C9C9C",null))));

        shields.setDetails(new AssemblageDetails(pieces));
        assemblageRepository.save(shields);
    }
}
