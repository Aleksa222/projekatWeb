package com.Projekat.Dostava.controller;

import com.Projekat.Dostava.dto.IzmenaPodatakaDto;
import com.Projekat.Dostava.dto.LoginDto;
import com.Projekat.Dostava.dto.LokacijaDto;
import com.Projekat.Dostava.dto.RegisterDto;
import com.Projekat.Dostava.entity.Enum_pol;
import com.Projekat.Dostava.entity.Korisnik;
import com.Projekat.Dostava.entity.Lokacija;
import com.Projekat.Dostava.entity.Restoran;
import com.Projekat.Dostava.repository.KorisnikRepository;
import com.Projekat.Dostava.service.KorisnikService;
import com.Projekat.Dostava.service.RestoranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Set;

@RestController
public class KorisnikRestController {
    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private RestoranService restoranService;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @GetMapping("/api/")
    public String welcome() {
        return "Pozdrav iz apija!";
    }

    //KORISNICKI DEO KOJEM MOGU PRISTUPITI SVI KORISNICI BEZ OBZIRA NA ULOGU

    @PostMapping("/api/register")
    public  ResponseEntity<String> register(@RequestBody RegisterDto registerDto, HttpSession session){
        if(registerDto.getKorisnicko_ime().isEmpty() ||
                registerDto.getLozinka().isEmpty() ||
                registerDto.getIme().isEmpty()     ||
                registerDto.getPrezime().isEmpty() ||
                !registerDto.getPol().getDeclaringClass().isEnum())
        {
            return new ResponseEntity<>("Invalid login data", HttpStatus.BAD_REQUEST);
        }

        Korisnik registeredKorisnik = korisnikService.register(registerDto.getKorisnicko_ime(),registerDto.getLozinka(),registerDto.getIme(),registerDto.getPrezime(),registerDto.getPol());

        if(registeredKorisnik == null){
            return new ResponseEntity<>("Korisnik vec postoji!", HttpStatus.BAD_REQUEST);
        }
        session.setAttribute("Korisnik", registeredKorisnik); //kasnije se moze pristupiti Korisniku uz pomoc stringa "Korisnik"
        return ResponseEntity.ok("Uspesna registracija");
    }

    @PutMapping("/api/promenaPodataka")
    public ResponseEntity<Korisnik> promenaPodataka(@RequestBody IzmenaPodatakaDto izmenaPodatakaDto,HttpSession session){
        Korisnik logged = (Korisnik) session.getAttribute("Korisnik");

        if(logged == null){
            return new ResponseEntity("Morate biti ulogovani da bi promenili podatke.",HttpStatus.NOT_FOUND);
        } else                           //omogucavanje da korisnik promeni samo neke od podataka, ne mora sve
        {
            if(izmenaPodatakaDto.getKorisnicko_ime() != null && !izmenaPodatakaDto.getKorisnicko_ime().isEmpty()){
                logged.setKorisnicko_ime(izmenaPodatakaDto.getKorisnicko_ime());
            }
            if(izmenaPodatakaDto.getLozinka() != null && !izmenaPodatakaDto.getLozinka().isEmpty()){
                logged.setLozinka(izmenaPodatakaDto.getLozinka());
            }
            if(izmenaPodatakaDto.getIme() != null && !izmenaPodatakaDto.getIme().isEmpty()){
                logged.setIme(izmenaPodatakaDto.getIme());
            }
            if(izmenaPodatakaDto.getPrezime() != null && !izmenaPodatakaDto.getPrezime().isEmpty()){
                logged.setPrezime(izmenaPodatakaDto.getPrezime());
            }
            if(izmenaPodatakaDto.getPol() == Enum_pol.MUSKI || izmenaPodatakaDto.getPol() == Enum_pol.ZENSKI){
                logged.setPol(izmenaPodatakaDto.getPol());
            }
            if(izmenaPodatakaDto.getDatum() != null){
                logged.setDatum_rodjenja(izmenaPodatakaDto.getDatum());
            }

            final Korisnik izmenjenKorisnik = korisnikService.save(logged); //ne moze da se modifikuje
            return ResponseEntity.ok(izmenjenKorisnik);
        }
    }

}
