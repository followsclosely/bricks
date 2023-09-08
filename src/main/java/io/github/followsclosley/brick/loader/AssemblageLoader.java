package io.github.followsclosley.brick.loader;

import io.github.followsclosley.brick.data.Assemblage;
import io.github.followsclosley.brick.data.AssemblagePiece;
import io.github.followsclosley.brick.data.repository.AssemblageRepository;
import io.github.followsclosley.brick.data.repository.PieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Order(6)
@Component()
@Scope("prototype")
@ConditionalOnExpression("${catalog.load-on-startup:false}")
public class AssemblageLoader implements ApplicationRunner {

    @Autowired
    private AssemblageRepository assemblageRepository;

    @Autowired
    private PieceRepository pieceRepository;

    public void run(ApplicationArguments args) throws Exception {
        Assemblage shields = new Assemblage();
        shields.setName("Stink");
        ArrayList<AssemblagePiece> pieces = new ArrayList();
        pieces.add(new AssemblagePiece(true, pieceRepository.getReferenceById("3846pb011")));
        shields.setPieces(pieces);
        assemblageRepository.save(shields);
    }
}
