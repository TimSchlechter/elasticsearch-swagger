package org.elasticsearch.routes;

import net.itimothy.rest.description.Route;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.routes.index.IndexRoutesProvider;
import org.elasticsearch.routes.root.RootRoutesProvider;

import java.util.List;

/**
 * Provides Elasticsearch's API metadata in a generic format
 */
public class ElasticsearchRoutesProvider extends RoutesProvider {

    private final RoutesProvider routesProvider;

    public ElasticsearchRoutesProvider(Client client, String indexOrAlias) {
        this(client, new ModelsCatalog(client, indexOrAlias), indexOrAlias);
    }

    public ElasticsearchRoutesProvider(Client client, ModelsCatalog modelsCatalog, String indexOrAlias) {
        super("Elasticsearch", client, modelsCatalog);

        routesProvider = StringUtils.isBlank(indexOrAlias)
            ? new RootRoutesProvider(client, getModelsCatalog())
            : new IndexRoutesProvider(client, getModelsCatalog(), indexOrAlias);
    }

    @Override
    protected List<Route> getRoutesInternal() {
        return routesProvider.getRoutes();
    }
}
