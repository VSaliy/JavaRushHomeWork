package com.javarush.test.level28.lesson15.big01.model;

import com.javarush.test.level28.lesson15.big01.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 20.04.16.
 */
public class MoikrugStrategy implements Strategy
{
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?q=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString)
    {
        List<Vacancy> list = new ArrayList<>();
        int page = 1;

        while(true)
        {
            try
            {
                //Получаем документ для парсинга и увеличиваем счетчик страниц
                Document document = getDocument("Kiev", page);

                //делаем выборку с указанными аттрибутами
                Elements elements = document.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");

                //Если вакансий нет - выходим из цикла
                if(elements.size() == 0)
                    break;

                //парсим
                for (Element element : elements)
                {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").text());
                    vacancy.setSalary(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").text());
                    vacancy.setCity(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text());
                    vacancy.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text());
                    vacancy.setSiteName(document.title());
                    vacancy.setUrl(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").attr("href"));

                    list.add(vacancy);
                }
                page++;
            }
            catch (IOException e)
            {
            }
        }



        return list;
    }

    protected Document getDocument(String searchString, int page) throws IOException
    {

        return Jsoup.connect(String.format(URL_FORMAT, searchString, page))
                .userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (HTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36")
                .referrer("http://hh.ru/search/vacancy?text=java+%D0%BA%D0%B8%D0%B5%D0%B2s")
                .get();
    }
}
