/*******************************************************************************
 * Copyright (c) 2010-2013, Embraer S.A., Budapest University of Technology and Economics
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors: 
 *     Marton Bur, Abel Hegedus, Akos Horvath - initial API and implementation 
 *******************************************************************************/
package hu.bme.mit.massif.simulink.api.util;

import java.util.List;
import java.util.Set;

import org.eclipse.incquery.runtime.api.IPatternMatch;
import org.eclipse.incquery.runtime.api.IncQueryMatcher;
import org.eclipse.incquery.runtime.base.itc.igraph.IBiDirectionalGraphDataSource;
import org.eclipse.incquery.runtime.base.itc.igraph.IGraphObserver;

import com.google.common.collect.ImmutableList;

public class PathMatcherGraphDataSource<Match extends IPatternMatch> implements IBiDirectionalGraphDataSource<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6674505895210661953L;
	private IncQueryMatcher<Match> matcher;
	private String first;
	private String second;
	private Object targetObject;

	public PathMatcherGraphDataSource(IncQueryMatcher<Match> matcher) {
		this.matcher = matcher;
		first = matcher.getParameterNames().get(0);
		second = matcher.getParameterNames().get(1);
	}
	
	public void setTarget(Object target) {
		this.targetObject = target;
	}
	
	@Override
	public void attachObserver(IGraphObserver<Object> go) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void detachObserver(IGraphObserver<Object> go) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Object> getAllNodes() {

		Set<Object> allNodes = matcher.getAllValues(first);
		allNodes.addAll(matcher.getAllValues(second));
		return allNodes;
	}

	@Override
	public List<Object> getTargetNodes(Object source) {
		Match match = matcher.newMatch(source, null, targetObject);
		return ImmutableList.copyOf(matcher.getAllValues(second, match));
	}

	@Override
	public List<Object> getSourceNodes(Object target) {
		Match match = matcher.newMatch(null, target, targetObject);
		return ImmutableList.copyOf(matcher.getAllValues(first, match));
	}

}
