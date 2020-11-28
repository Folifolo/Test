package WikipediaParser;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class WikipediaParser {

    final static String WIKIPEDIA_URL = "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=";
    final static int MAX_RESULTS = 10;
    final static int CONNECTION_TIME = 1000;


    public static void main(String[] args) {
        String request;
        try{
            request = args[0];
        } catch (ArrayIndexOutOfBoundsException o) {
            System.out.println("You need to enter a request");
            return;
        }
        try {
            request = URLEncoder.encode(
                    "\"" + request + "\"",
                    java.nio.charset.StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported characters in request");
        }


        String[] results = WikiSearchTitles(request);
        if(results != null)
            for(int i = 0; i < results.length; i++)
                System.out.println(results[i]);

    }

    private static String[] WikiSearchTitles(String request) {
        Gson gson = new Gson();
        String json;
        String[] titles = null;


        final HttpURLConnection connection;
        try {
            final URL url = new URL(WIKIPEDIA_URL + request);
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            System.out.println("Wrong url");
            return null;
        }

        try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            json = br.readLine();
            WikiSearch searchResult = gson.fromJson(json, WikiSearch.class);

            System.out.println("Results found: " + searchResult.query.searchinfo.totalhits);

            if(searchResult.query.searchinfo.totalhits > 0){
                titles = new String[searchResult.query.search.length];

                for(int i = 0; i < titles.length; i++) {
                    titles[i] = searchResult.query.search[i].title;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return titles;
    }


    static class WikiSearch {
            final String batchcomplete;
            final Continue _continue;
            final Query query;

            
            WikiSearch(String batchcomplete, Continue _continue, Query query){
                this.batchcomplete = batchcomplete;
                this._continue = _continue;
                this.query = query;
            }

            static final class Continue {
                final long sroffset;
                final String _continue;

                
                Continue(long sroffset, String _continue){
                    this.sroffset = sroffset;
                    this._continue = _continue;
                }
            }

            static final class Query {
                final Searchinfo searchinfo;
                final Search[] search;

                
                Query(Searchinfo searchinfo, Search[] search) {
                    this.searchinfo = searchinfo;
                    this.search = search;
                }

                static final class Searchinfo {
                    final int totalhits;

                    
                    Searchinfo(int totalhits) {
                        this.totalhits = totalhits;
                    }
                }

                static final class Search {
                    final long ns;
                    final String title;
                    final long pageid;
                    final long size;
                    final long wordcount;
                    final String snippet;
                    final String timestamp;

                    
                    Search(long ns, String title, long pageid, long size,
                                  long wordcount, String snippet, String timestamp) {
                        this.ns = ns;
                        this.title = title;
                        this.pageid = pageid;
                        this.size = size;
                        this.wordcount = wordcount;
                        this.snippet = snippet;
                        this.timestamp = timestamp;
                    }
                }
            }
    }
}
