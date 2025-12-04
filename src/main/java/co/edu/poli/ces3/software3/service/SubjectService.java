package co.edu.poli.ces3.software3.service;

import co.edu.poli.ces3.software3.dbo.StatusEnum;
import co.edu.poli.ces3.software3.dbo.Subject;
import com.google.gson.JsonElement;
import java.security.SecureRandom;

import java.util.Vector;



public class SubjectService {

    private Vector<Subject> subjects;

    public SubjectService() {
        subjects = new Vector<>();
        subjects.add(new Subject("API-123", new StringBuilder("CES3")));
        subjects.add(new Subject("DT-123", new StringBuilder("Bases de datos 2")));
    }

    public Vector find() {

        return this.subjects;
    }

    private static String generarId() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder id = new StringBuilder(3);

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(caracteres.length());
            id.append(caracteres.charAt(index));
        }

        return id.toString();
    }

    public Subject findById(String id){
        return subjects.stream()
                .filter(subject -> subject.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Subject add(JsonElement jsonsubject){
        Subject s = new Subject();
        s.setId(generarId());
        s.setName(jsonsubject.getAsJsonObject().get("name").getAsString());
        s.setCode(jsonsubject.getAsJsonObject().get("code").getAsString());
        s.setDescription(new StringBuilder(jsonsubject.getAsJsonObject().get("description").getAsString()));
        s.setStatus(StatusEnum.ACTIVE);

        subjects.add(s);

        return s;
    }

    public Subject update(JsonElement jsonsubject){
        Subject s = new Subject();

        if (jsonsubject.getAsJsonObject().get("id").getAsString() != null) {
            String subjectid = jsonsubject.getAsJsonObject().get("id").getAsString();
            s = this.findById(subjectid);
            //System.out.println(subjectid);
            this.subjects.remove(s);
        } else {
            return null;
        }


        if (jsonsubject.getAsJsonObject().get("name") != null) {
            s.setName(jsonsubject.getAsJsonObject().get("name").getAsString());
        }

        if (jsonsubject.getAsJsonObject().get("code") != null ) {
            s.setCode(jsonsubject.getAsJsonObject().get("code").getAsString());
        }

        if  (jsonsubject.getAsJsonObject().get("description") != null) {
            s.setDescription(new StringBuilder(jsonsubject.getAsJsonObject().get("description").getAsString()));
        }

        this.subjects.add(s);

        return s;

    }


}
