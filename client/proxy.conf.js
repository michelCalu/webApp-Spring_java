const PROXY_CONFIG = [
  {
    context: [
      "/about",
      "/citizens",
      "/documents",
      "/requests",
      "/employees",
      "/auth",
      "/municipalities",
      "/departments",
      "/events",
      "/mandataries",
      "/companies"
    ],
    "target": "http://localhost:8080",
    "secure": false,
    "logLevel": "debug"
  }
  ]

module.exports = PROXY_CONFIG;
