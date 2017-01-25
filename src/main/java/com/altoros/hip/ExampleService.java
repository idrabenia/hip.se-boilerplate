package com.altoros.hip;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.hip.sdk.api.Api;
import se.hip.sdk.api.GetCareContactsBuilder;
import se.hip.sdk.api.RegisterPdlConsentBuilder;
import se.hip.sdk.api.carecontacts.CareContact;
import se.hip.sdk.api.core.*;
import se.hip.sdk.api.operation.GetCareContacts;
import se.hip.sdk.api.operation.RegisterPdlConsent;
import se.hip.sdk.api.query.result.DataResultSet;

/**
 * @author Ilya Drabenia
 */
@Service
public class ExampleService {

    @Autowired
    private Api ipv;

    public void fetchCareContacts() throws Exception {
        final CareActorPrincipal careActor = PrincipalFactory.createCareActorPrincipal(
            "sessionId",
            "Vård och behandling",
            "assignmentId",
            "careActorId",
            "careUnitId",
            "careProviderId",
            "careActorHsaTitle",
            "careActorPrescriptionCode",
            "Läsa;alla;SJF"
        );

        final SubjectOfCare soc = DefaultSubjectOfCare.create("191212121212");

        // Register a consent for
        // Tolvan (191212121212)
        final RegisterPdlConsent postConsent = ipv.getOperationBuilder(RegisterPdlConsentBuilder.class)
            .as(careActor)
            .forSubjectOfCare(soc)
            .isEmergency(false)
            .isForRequestingCareActorOnly(false)
            .validFrom(DateTime.now().minusYears(1).toDate())
            .expiresAt(DateTime.now().toDate())
            .build();

        final Response resp = postConsent.execute();
        if (resp.getStatus().getErrors().isEmpty()) {

            // Build query
            final GetCareContacts request = ipv.getOperationBuilder(GetCareContactsBuilder.class)
                .as(careActor)
                .limit(10)
                .startAt(0)
                .withSubjectOfCare(soc)
                .forceFetch()
                .build();

            // Fetch data
            Response<DataResultSet<CareContact>> response = request.execute();

            // Do something with the result
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response));
        }
    }

}