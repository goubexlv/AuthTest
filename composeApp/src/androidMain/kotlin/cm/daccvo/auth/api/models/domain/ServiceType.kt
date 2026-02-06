package cm.horion.models.domain

enum class ServiceType(val audience: String) {
    CV("cv-service"),
    ENTERPRISE("enterprise-service")
}