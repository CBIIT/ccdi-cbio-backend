package org.cbioportal.legacy.web.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.LinkedHashMap;
import java.util.Map;
import org.cbioportal.legacy.model.CancerStudy;
import org.cbioportal.legacy.model.ClinicalAttribute;
import org.cbioportal.legacy.model.ClinicalAttributeCount;
import org.cbioportal.legacy.model.ClinicalData;
import org.cbioportal.legacy.model.ClinicalDataCount;
import org.cbioportal.legacy.model.ClinicalEvent;
import org.cbioportal.legacy.model.ClinicalEventData;
import org.cbioportal.legacy.model.CopyNumberSeg;
import org.cbioportal.legacy.model.DataAccessToken;
import org.cbioportal.legacy.model.DiscreteCopyNumberData;
import org.cbioportal.legacy.model.Gene;
import org.cbioportal.legacy.model.GenePanel;
import org.cbioportal.legacy.model.GenePanelToGene;
import org.cbioportal.legacy.model.Geneset;
import org.cbioportal.legacy.model.GenesetCorrelation;
import org.cbioportal.legacy.model.GenesetMolecularData;
import org.cbioportal.legacy.model.Gistic;
import org.cbioportal.legacy.model.GisticToGene;
import org.cbioportal.legacy.model.MolecularProfile;
import org.cbioportal.legacy.model.MutSig;
import org.cbioportal.legacy.model.Mutation;
import org.cbioportal.legacy.model.MutationSpectrum;
import org.cbioportal.legacy.model.NamespaceDataCount;
import org.cbioportal.legacy.model.Patient;
import org.cbioportal.legacy.model.ResourceDefinition;
import org.cbioportal.legacy.model.Sample;
import org.cbioportal.legacy.model.SampleList;
import org.cbioportal.legacy.model.StructuralVariant;
import org.cbioportal.legacy.model.TypeOfCancer;
import org.cbioportal.legacy.service.util.CustomAttributeWithData;
import org.cbioportal.legacy.service.util.CustomDataSession;
import org.cbioportal.legacy.utils.removeme.Session;
import org.cbioportal.legacy.web.mixin.CancerStudyMixin;
import org.cbioportal.legacy.web.mixin.ClinicalAttributeCountMixin;
import org.cbioportal.legacy.web.mixin.ClinicalAttributeMixin;
import org.cbioportal.legacy.web.mixin.ClinicalDataCountMixin;
import org.cbioportal.legacy.web.mixin.ClinicalDataMixin;
import org.cbioportal.legacy.web.mixin.ClinicalEventDataMixin;
import org.cbioportal.legacy.web.mixin.ClinicalEventMixin;
import org.cbioportal.legacy.web.mixin.CopyNumberSegMixin;
import org.cbioportal.legacy.web.mixin.DataAccessTokenMixin;
import org.cbioportal.legacy.web.mixin.DiscreteCopyNumberDataMixin;
import org.cbioportal.legacy.web.mixin.GeneMixin;
import org.cbioportal.legacy.web.mixin.GenePanelMixin;
import org.cbioportal.legacy.web.mixin.GenePanelToGeneMixin;
import org.cbioportal.legacy.web.mixin.GenesetCorrelationMixin;
import org.cbioportal.legacy.web.mixin.GenesetMixin;
import org.cbioportal.legacy.web.mixin.GenesetMolecularDataMixin;
import org.cbioportal.legacy.web.mixin.GisticMixin;
import org.cbioportal.legacy.web.mixin.GisticToGeneMixin;
import org.cbioportal.legacy.web.mixin.MolecularProfileMixin;
import org.cbioportal.legacy.web.mixin.MutSigMixin;
import org.cbioportal.legacy.web.mixin.MutationMixin;
import org.cbioportal.legacy.web.mixin.MutationSpectrumMixin;
import org.cbioportal.legacy.web.mixin.NamespaceDataCountMixin;
import org.cbioportal.legacy.web.mixin.PatientMixin;
import org.cbioportal.legacy.web.mixin.ResourceDefinitionMixin;
import org.cbioportal.legacy.web.mixin.SampleListMixin;
import org.cbioportal.legacy.web.mixin.SampleMixin;
import org.cbioportal.legacy.web.mixin.SessionDataMixin;
import org.cbioportal.legacy.web.mixin.SessionMixin;
import org.cbioportal.legacy.web.mixin.StructuralVariantMixin;
import org.cbioportal.legacy.web.mixin.TypeOfCancerMixin;
import org.cbioportal.legacy.web.parameter.PageSettings;
import org.cbioportal.legacy.web.parameter.PageSettingsData;
import org.cbioportal.legacy.web.parameter.StudyPageSettings;
import org.cbioportal.legacy.web.parameter.VirtualStudy;
import org.cbioportal.legacy.web.parameter.VirtualStudyData;
import tools.jackson.databind.cfg.EnumFeature;
import tools.jackson.databind.json.JsonMapper;

// Primary JsonMapper for Spring Boot 4 / Jackson 3 HTTP conversion.
public class CustomObjectMapper extends JsonMapper {

  public CustomObjectMapper() {
    super(builderWithMixins());
  }

  public static JsonMapper.Builder builderWithMixins() {
    JsonMapper.Builder builder =
        JsonMapper.builder()
            .changeDefaultPropertyInclusion(
                incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL))
            .enable(EnumFeature.WRITE_ENUMS_USING_TO_STRING);
    mixins().forEach(builder::addMixIn);
    return builder;
  }

  static Map<Class<?>, Class<?>> mixins() {
    Map<Class<?>, Class<?>> mixinMap = new LinkedHashMap<>();
    mixinMap.put(CancerStudy.class, CancerStudyMixin.class);
    mixinMap.put(ClinicalAttribute.class, ClinicalAttributeMixin.class);
    mixinMap.put(ClinicalAttributeCount.class, ClinicalAttributeCountMixin.class);
    mixinMap.put(ClinicalData.class, ClinicalDataMixin.class);
    mixinMap.put(ClinicalDataCount.class, ClinicalDataCountMixin.class);
    mixinMap.put(ClinicalEvent.class, ClinicalEventMixin.class);
    mixinMap.put(ClinicalEventData.class, ClinicalEventDataMixin.class);
    mixinMap.put(CopyNumberSeg.class, CopyNumberSegMixin.class);
    mixinMap.put(DataAccessToken.class, DataAccessTokenMixin.class);
    mixinMap.put(DiscreteCopyNumberData.class, DiscreteCopyNumberDataMixin.class);
    mixinMap.put(StructuralVariant.class, StructuralVariantMixin.class);
    mixinMap.put(Gene.class, GeneMixin.class);
    mixinMap.put(GenePanel.class, GenePanelMixin.class);
    mixinMap.put(GenePanelToGene.class, GenePanelToGeneMixin.class);
    mixinMap.put(Geneset.class, GenesetMixin.class);
    mixinMap.put(GenesetMolecularData.class, GenesetMolecularDataMixin.class);
    mixinMap.put(GenesetCorrelation.class, GenesetCorrelationMixin.class);
    mixinMap.put(MolecularProfile.class, MolecularProfileMixin.class);
    mixinMap.put(Gistic.class, GisticMixin.class);
    mixinMap.put(GisticToGene.class, GisticToGeneMixin.class);
    mixinMap.put(Mutation.class, MutationMixin.class);
    mixinMap.put(MutationSpectrum.class, MutationSpectrumMixin.class);
    mixinMap.put(MutSig.class, MutSigMixin.class);
    mixinMap.put(NamespaceDataCount.class, NamespaceDataCountMixin.class);
    mixinMap.put(PageSettings.class, SessionMixin.class);
    mixinMap.put(PageSettingsData.class, SessionDataMixin.class);
    mixinMap.put(Patient.class, PatientMixin.class);
    mixinMap.put(Sample.class, SampleMixin.class);
    mixinMap.put(SampleList.class, SampleListMixin.class);
    mixinMap.put(Session.class, SessionMixin.class);
    mixinMap.put(StudyPageSettings.class, SessionDataMixin.class);
    mixinMap.put(TypeOfCancer.class, TypeOfCancerMixin.class);
    mixinMap.put(ResourceDefinition.class, ResourceDefinitionMixin.class);
    mixinMap.put(VirtualStudy.class, SessionMixin.class);
    mixinMap.put(VirtualStudyData.class, SessionDataMixin.class);
    mixinMap.put(CustomAttributeWithData.class, SessionDataMixin.class);
    mixinMap.put(CustomDataSession.class, SessionMixin.class);
    return mixinMap;
  }
}
