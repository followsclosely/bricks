package io.github.followsclosley.brick;


import org.apache.commons.csv.CSVFormat;
import org.dajlab.bricklinkapi.v1.enumeration.Type;
import org.dajlab.bricklinkapi.v1.service.IBricklinkService;
import org.dajlab.bricklinkapi.v1.service.impl.BricklinkServiceImpl;
import org.dajlab.bricklinkapi.v1.vo.Item;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegration;

@EnableIntegration
@SpringBootApplication
public class BrickApplication {
    public static void main(String[] args)  {


        SpringApplication.run(BrickApplication.class, args);

        extracted();
    }

    private static void extracted() {
        String consumerKey = "9957F7ED544F4E209EB78B8EC610ADC2";
        String consumerSecret = "807B26A39C0449EB94DE71B997A6D4EA";
        String tokenValue = "789E9553A6AF4390A5F3141870E3846C";
        String tokenSecret = "1E99729131FA4CCE82A6A3AA4C04F70D";


        try {
            IBricklinkService service = new BricklinkServiceImpl(consumerKey, consumerSecret, tokenValue, tokenSecret);
            //Item item = service.getCatalogItem().getItem(Type.PART, "3001");
            //System.out.println();


            Item item = service.getCatalogItem().getItem(Type.SET, "10305-1");
            System.out.println();

            //"https://www.bricklink.com/catalogDownload.asp?a=a&itemType=O&selYear=Y&selWeight=Y&selDim=Y&viewType=4&itemTypeInv=S&itemNo=10305-1&downloadType=T"
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Bean
    public CSVFormat csvParser() {
        return CSVFormat.DEFAULT.builder()
                .setHeader().setSkipHeaderRecord(true)
                .setDelimiter("\t")
                .setIgnoreEmptyLines(true)
                .setQuote(null)
                .build();
    }
}
