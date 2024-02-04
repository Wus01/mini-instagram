package instagram.miniinstagram.config;

import instagram.miniinstagram.utils.FileStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Slf4j
@Configuration
public class FileConfig {
    @Value("${file.dir}")
    private String fileDir;

    @Bean
    public FileStore fileStore()
    {
        File folder = new File(fileDir);
        if(!folder.exists())            //파일이 없다면 파일 생성하기
        {
            try
            {
                folder.mkdir();
                log.info("[+] Success To Create");
            }
            catch(Exception e)
            {
                log.error("error", e);
            }
        }
        else
        {
            log.info("[-] Already Existed The File");
        }
        return new FileStore();
    }
}
