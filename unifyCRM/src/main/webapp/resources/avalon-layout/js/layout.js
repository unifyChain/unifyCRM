/** 
 * PrimeFaces Avalon Layout
 */
PrimeFaces.widget.Avalon = PrimeFaces.widget.BaseWidget.extend({
    
    init: function(cfg) {
        this._super(cfg);
        this.wrapper = $(document.body).children('.layout-wrapper');
        this.topbar = $('body > .layout-wrapper .topbar');
        this.menu = this.jq;
        this.menuContainer = this.wrapper.children('.layout-menu-container');
        this.menuWrapper = this.menu.closest('.layout-menu');
        this.menulinks = this.menu.find('a');
        this.profileButton = $('#profile-options');
        this.profileMenu = $('#profile-menu');
        this.topbarItems = this.topbar.find('.topbar-items');
        this.topbarLinks = this.topbarItems.find('> li > a');
        this.menuButton = $('#menu-button');
        this.topbarMenuButton = $('#topbar-menu-button');
        this.menuActive = false;
        this.topbarLinkClick = false;
        this.topbarMenuClick = false;
        this.menuButtonClick = false;
        this.isMobileDev = this.isMobileDevice();


        this.configButton = $('#layout-config-button');
        this.configMenu = $('#layout-config');
        this.configMenuClose = this.configMenu.find('.layout-config-close');

        this._bindEvents();
        
        this.restoreMenuState();
    },
    
    _bindEvents: function() {
        var $this = this;
        
        this.menuButton.off('click.menuButton').on('click.menuButton', function(e) {
            $this.menuButton.removeClass('menu-button-rotate');
            $this.topbarItems.removeClass('topbar-items-visible');
            
            $this.menuButtonClick = true;
            
            //overlay
            if($this.wrapper.hasClass('menu-layout-overlay')) {
                $this.wrapper.toggleClass('layout-menu-overlay-active');
                
                if($this.wrapper.hasClass('layout-menu-overlay-active')) {
                    $this.enableModal();
                    $this.enableSwipe();
                }
                else {
                    $this.disableModal();
                    $this.disableSwipe();
                    $this.menuButton.addClass('menu-button-rotate');
                }
            }
            //static
            else {
                if($this.isDesktop()) {
                    $this.wrapper.toggleClass('layout-menu-static-inactive')
                }
                else {
                    if($this.wrapper.hasClass('layout-menu-static-active')) {
                        $this.wrapper.removeClass('layout-menu-static-active');
                        $this.disableModal();
                        $this.disableSwipe();
                    }
                    else {
                        $this.wrapper.addClass('layout-menu-static-active');
                        $this.wrapper.removeClass('layout-menu-static-inactive');
                        $this.enableModal();
                        $this.enableSwipe();
                    }
                }
                
                if($this.wrapper.hasClass('layout-menu-static-inactive')) {
                    $this.menuButton.addClass('menu-button-rotate');
                }
            }
            
            e.preventDefault();
        });
        
        this.topbarMenuButton.off('click.topbarButton').on('click.topbarButton', function(e) {
            $this.topbarMenuClick = true;
            $this.topbarItems.find('ul').removeClass('fadeInDown fadeOutUp');
            
            if($this.wrapper.hasClass('layout-menu-overlay-active')||$this.wrapper.hasClass('layout-menu-static-active')) {
                $this.menuButton.removeClass('menu-button-rotate');
                $this.wrapper.removeClass('layout-menu-overlay-active layout-menu-static-active');
                $this.disableModal();
            }

            if($this.topbarItems.hasClass('topbar-items-visible')) {
                $this.topbarItems.addClass('fadeOutUp');
                
                setTimeout(function() {
                    $this.topbarItems.removeClass('fadeOutUp topbar-items-visible');
                },500);
            }
            else {
                $this.topbarItems.addClass('topbar-items-visible fadeInDown');
            }
            
            e.preventDefault();
        });
        
        this.menulinks.off('click.menulink').on('click.menulink', function(e) {
            var link = $(this),
            item = link.parent(),
            submenu = item.children('ul'),
            horizontal = $this.isHorizontal(),
            slim = $this.isSlim();
            
            $this.menuClick = true;
                                                 
            if(item.hasClass('active-menuitem')) {
                if(submenu.length) {
                    item.removeClass('active-menuitem');
                    
                    if(horizontal || slim) {
                        if(item.parent().is($this.jq)) {
                            $this.menuActive = false;
                        }
                        
                        submenu.hide();
                    }
                    else {
                        submenu.slideUp();
                    }
                }
            }
            else {
                if(horizontal || slim) {
                    $this.deactivateItems(item.siblings());
                    item.addClass('active-menuitem');
                    $this.menuActive = true;
                    submenu.show();
                }
                else {
                    $this.deactivateItems(item.siblings(), true);
                    $this.activate(item);

                    $.cookie('avalon_menu_scroll_state', link.attr('href') + ',' + $this.menuContainer.scrollTop(), { path: '/' });
                }
            }
                                    
            if(submenu.length) {
                e.preventDefault();
            }
        });
        
        this.menu.find('> li').off('mouseenter.menuitem').on('mouseenter.menuitem', function(e) {    
            if($this.isHorizontal()||$this.isSlim()) {
                var item = $(this);
                
                if(!item.hasClass('active-menuitem')) {
                    $this.menu.find('.active-menuitem').removeClass('active-menuitem');
                    $this.menu.find('ul:visible').hide();
                    $this.menu.find('.ink').remove();
                    
                    if($this.menuActive) {
                        item.addClass('active-menuitem');
                        item.children('ul').show();
                    }
                }
            }
        });
        
        this.profileButton.off('click.profileButton').on('click.profileButton', function(e) {
            var profile = $this.profileMenu.prev('.profile'),
            expanded = profile.hasClass('profile-expanded');
            
            $this.profileMenu.slideToggle();
            $this.profileMenu.prev('.profile').toggleClass('profile-expanded');
            $this.setInlineProfileState(!expanded);
            
            e.preventDefault();
        });
                
        this.topbarLinks.off('click.topbarLink').on('click.topbarLink', function(e) {
            var link = $(this),
            item = link.parent(),
            submenu = link.next();
            
            $this.topbarLinkClick = true;

            item.siblings('.active-top-menu').removeClass('active-top-menu');
            if($this.wrapper.hasClass('layout-menu-overlay-active')) {
                $this.menuButton.removeClass('menu-button-rotate');
                $this.wrapper.removeClass('layout-menu-overlay-active');
                $this.disableModal();
            }

            if($this.isDesktop()) {
                if(submenu.length) {
                    if(item.hasClass('active-top-menu')) {
                        submenu.addClass('fadeOutUp');
                        
                        setTimeout(function() {
                            item.removeClass('active-top-menu'),
                            submenu.removeClass('fadeOutUp');
                        },500);
                    }
                    else {
                        item.addClass('active-top-menu');
                        submenu.addClass('fadeInDown');
                    }
                }
            }
            else {
                item.children('ul').removeClass('fadeInDown fadeOutUp');
                item.toggleClass('active-top-menu');
            }
            
            var href = link.attr('href');
            if(href && href !== '#') {
                window.location.href = href;
            }
            
            e.preventDefault();         
        });
        
        $this.topbarItems.children('.search-item').off('click.topbar').on('click.topbar', function(e) {
            $this.topbarLinkClick = true;
        });
        
        $this.wrapper.find('.layout-menu-container').off('click.topbar').on('click.topbar', function(e) {
            $this.menuContainerClick = true;
        }); 

        this.bindConfigEvents();
        
        $(document.body).off('click.layoutBody').on('click.layoutBody', function() {
            if(($this.isHorizontal() || $this.isSlim()) && !$this.menuClick) {
                $this.menu.find('.active-menuitem').removeClass('active-menuitem');
                $this.menu.find('ul:visible').hide();
                $this.menuActive = false;
            }
            
            if(($this.isOverlay() || $this.isSlim()) && !$this.menuContainerClick && !$this.menuButtonClick) {
                $this.wrapper.removeClass('layout-menu-overlay-active layout-menu-static-active');
                $this.disableModal();
                $this.menuActive = false;
            }
            
            if(!$this.topbarMenuClick && !$this.topbarLinkClick) {
                $this.topbarItems.find('.active-top-menu').removeClass('active-top-menu');
            }
            
            if(!$this.topbarMenuClick && !$this.topbarLinkClick) {
                $this.topbarItems.removeClass('topbar-items-visible');
            }

            if (!$this.configMenuClicked) {
                $this.configMenu.removeClass('layout-config-active');
            }
            
            $this.menuClick = false;
            $this.topbarLinkClick = false;
            $this.topbarMenuClick = false;
            $this.menuButtonClick = false;
            $this.menuContainerClick = false;
            $this.configMenuClicked = false;
        });
    },

    bindConfigEvents: function() {
        var $this = this;
        var changeConfigMenuState = function(e) {
            this.toggleClass(this.configMenu, 'layout-config-active');
            
            this.configMenuClicked = true;
            e.preventDefault();
        };

        this.configButton.off('click.config').on('click.config', changeConfigMenuState.bind(this));
        this.configMenuClose.off('click.config').on('click.config', changeConfigMenuState.bind(this));
        
        this.configMenu.off('click.rightpanel').on('click.rightpanel', function() {
            $this.configMenuClicked = true;
        });
    },

    activate: function(item) {
        var submenu = item.children('ul');
        item.addClass('active-menuitem');

        if(submenu.length) {
            submenu.slideDown();
        }
    },
    
    deactivate: function(item) {
        var submenu = item.children('ul');
        item.removeClass('active-menuitem');
        
        if(submenu.length) {
            submenu.hide();
        }
    },
        
    deactivateItems: function(items, animate) {
        var $this = this;
        
        for(var i = 0; i < items.length; i++) {
            var item = items.eq(i),
            submenu = item.children('ul');
            
            if(submenu.length) {
                if(item.hasClass('active-menuitem')) {
                    var activeSubItems = item.find('.active-menuitem');
                    item.removeClass('active-menuitem');
                    item.find('.ink').remove();
                    
                    if(animate) {
                        submenu.slideUp('normal', function() {
                            $(this).parent().find('.active-menuitem').each(function() {
                                $this.deactivate($(this));
                            });
                        });
                    }
                    else {
                        submenu.hide();
                        item.find('.active-menuitem').each(function() {
                            $this.deactivate($(this));
                        });
                    }
                }
                else {
                    item.find('.active-menuitem').each(function() {
                        var subItem = $(this);
                        $this.deactivate(subItem);
                    });
                }
            }
            else if(item.hasClass('active-menuitem')) {
                $this.deactivate(item);
            }
        }
    },
    
    setInlineProfileState: function(expanded) {
        if(expanded)
            $.cookie('avalon_inlineprofile_expanded', "1", {path: '/'});
        else
            $.removeCookie('avalon_inlineprofile_expanded', {path: '/'});
    },
    
    restoreMenuState: function() {
        var $this = this;

        if (!this.isSlim() && !this.wrapper.hasClass('menu-layout-horizontal')) {

            var link = this.menu.find('a[href^="' + this.cfg.pathname + '"]');
            if (link.length) {
                var menuitem = link.closest('li');
                menuitem.addClass('active-menuitem');

                var parentmenu = menuitem.parent('ul');
                if (parentmenu.length) {
                    parentmenu.slideDown();
                    parentmenu.parent('li').addClass('active-menuitem');
                }

                setTimeout(function() {
                    $this.restoreScrollState(menuitem);
                }, 100)
            }
        }
        
        var inlineProfileCookie = $.cookie('avalon_inlineprofile_expanded');
        if (inlineProfileCookie) {
            this.profileMenu.show().prev('.profile').addClass('profile-expanded');
        }
    },

    restoreScrollState: function(menuitem) {
        var scrollState = $.cookie('avalon_menu_scroll_state');
        if (scrollState) {
            var state = scrollState.split(',');
            if (state[0].startsWith(this.cfg.pathname) || this.isScrolledIntoView(menuitem, state[1])) {
                this.menuContainer.scrollTop(parseInt(state[1], 10));
            }
            else {
                this.scrollIntoView(menuitem.get(0));
                $.removeCookie('avalon_menu_scroll_state', { path: '/' });
            }
        }
        else if (!this.isScrolledIntoView(menuitem, menuitem.scrollTop())){
            this.scrollIntoView(menuitem.get(0));
        }
    },

    scrollIntoView: function(elem) {
        if (document.documentElement.scrollIntoView) {
            elem.scrollIntoView({ block: "nearest", inline: 'start' });

            var container = $('.layout-menu-container');
            var scrollTop = container.scrollTop();
            if (scrollTop > 0) {
                container.scrollTop(scrollTop + parseFloat(this.topbar.height()));
            }
        }
    },

    isScrolledIntoView: function(elem, scrollTop) {
        var viewBottom = parseInt(scrollTop, 10) + this.menuContainer.height();

        var elemTop = elem.position().top;
        var elemBottom = elemTop + elem.height();

        return ((elemBottom <= viewBottom) && (elemTop >= scrollTop));
    },
    
    enableModal: function() {
        this.modal = this.wrapper.append('<div class="layout-mask"></div>').children('.layout-mask');
    },
    
    disableModal: function() {
        if(this.modal) {
            this.modal.remove();
        }
    },
    
    enableSwipe: function() {
        if(this.isMobileDev) {
            var $this = this;
            this.menuWrapper.swipe({
                swipeLeft: function() {
                    $this.menuButton.click();
                }
            });
        }
    },
    
    disableSwipe: function() {
        if(this.isMobileDev) {
            this.menuWrapper.swipe('destroy');
        }
    },
    
    isHorizontal: function() {
        return this.wrapper.hasClass('menu-layout-horizontal') && this.isDesktop();
    },
    
    isSlim: function() {
        return this.isDesktop() && this.wrapper.hasClass('menu-layout-slim');
    },
    
    isOverlay: function() {
        return this.wrapper.hasClass('menu-layout-overlay') || (this.wrapper.hasClass('menu-layout-static') && !this.isDesktop());
    },
    
    isTablet: function() {
        var width = window.innerWidth;
        return width <= 1024 && width > 640;
    },

    isDesktop: function() {
        return window.innerWidth > 1024;
    },

    isMobile: function() {
        return window.innerWidth <= 640;
    },
  
    toggleClass: function(el, className) {
        if (el.hasClass(className)) {
            el.removeClass(className);
        }
        else {
            el.addClass(className);
        }
    },
    
    isMobileDevice: function() {
        return /android|webos|iphone|ipad|ipod|blackberry|iemobile|opera mini/i.test(window.navigator.userAgent.toLowerCase());
    },
    
    clearActiveItems: function() {
        var activeItems = this.jq.find('li.active-menuitem'),
        subContainers = activeItems.children('ul');

        activeItems.removeClass('active-menuitem');
        if(subContainers && subContainers.length) {
            subContainers.hide();
        }
    }

});

PrimeFaces.AvalonConfigurator = {

    changeLayout: function(layoutTheme) {
        var linkElement = $('link[href*="layout-"]');
        var href = linkElement.attr('href');
        var startIndexOf = href.indexOf('layout-') + 7;
        var endIndexOf = href.indexOf('.css');
        var currentColor = href.substring(startIndexOf, endIndexOf);

        this.replaceLink(linkElement, href.replace(currentColor, layoutTheme));
    },
    
    changeComponentsTheme: function(theme) {
        var library = 'primefaces-avalon';
        var linkElement = $('link[href*="theme.css"]');
        var href = linkElement.attr('href');
        var index = href.indexOf(library) + 1;
        var currentTheme = href.substring(index + library.length);

        this.replaceLink(linkElement, href.replace(currentTheme, theme));
    },

    changeMenuLayout: function(menuLayout) {
        var wrapper = $('.layout-wrapper');
        switch (menuLayout) {
            case 'horizontal':
                wrapper.removeClass('menu-layout-overlay layout-menu-static-inactive menu-layout-slim').addClass('menu-layout-static menu-layout-horizontal');
                break;

            case 'overlay':
                wrapper.removeClass('menu-layout-horizontal layout-menu-static-inactive menu-layout-static menu-layout-slim').addClass('menu-layout-overlay');
                break;

            case 'slim':
                wrapper.removeClass('menu-layout-overlay layout-menu-static-inactive menu-layout-horizontal').addClass('menu-layout-static menu-layout-slim');
                break;

            default:
                wrapper.removeClass('menu-layout-overlay layout-menu-static-inactive menu-layout-horizontal menu-layout-slim').addClass('menu-layout-static');
                break;
        }

        $('#menu-button').removeClass('menu-button-rotate');
        this.updateMenuState();
    },
    
    changeMenuToDark: function() {
        $('.layout-menu-container').removeClass('layout-menu-light').addClass('layout-menu-dark');
    },
    
    changeMenuToLight: function() {
        $('.layout-menu-container').removeClass('layout-menu-dark').addClass('layout-menu-light');
    },

    beforeResourceChange: function() {
        PrimeFaces.ajax.RESOURCE = null;    //prevent resource append
    },

    updateInputStyle: function(value) {
        if (value === 'filled')
            $(document.body).addClass('ui-input-filled');
        else
            $(document.body).removeClass('ui-input-filled');
    },

    replaceLink: function(linkElement, href) {
        PrimeFaces.ajax.RESOURCE = 'javax.faces.Resource';

        var isIE = this.isIE();

        if (isIE) {
            linkElement.attr('href', href);
        }
        else {
            var cloneLinkElement = linkElement.clone(false);

            cloneLinkElement.attr('href', href);
            linkElement.after(cloneLinkElement);
            
            cloneLinkElement.off('load').on('load', function() {
                linkElement.remove();
            });
        }
    },
    
    updateMenuState: function() {
        var menu = PF('avalonMenuWidget');
    
        if (menu) {
            menu.restoreMenuState();
        }
    },
    
    isIE: function() {
        return /(MSIE|Trident\/|Edge\/)/i.test(navigator.userAgent);
    }
};

/*!
 * jQuery Cookie Plugin v1.4.1
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2006, 2014 Klaus Hartl
 * Released under the MIT license
 */
(function (factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD (Register as an anonymous module)
		define(['jquery'], factory);
	} else if (typeof exports === 'object') {
		// Node/CommonJS
		module.exports = factory(require('jquery'));
	} else {
		// Browser globals
		factory(jQuery);
	}
}(function ($) {

	var pluses = /\+/g;

	function encode(s) {
		return config.raw ? s : encodeURIComponent(s);
	}

	function decode(s) {
		return config.raw ? s : decodeURIComponent(s);
	}

	function stringifyCookieValue(value) {
		return encode(config.json ? JSON.stringify(value) : String(value));
	}

	function parseCookieValue(s) {
		if (s.indexOf('"') === 0) {
			// This is a quoted cookie as according to RFC2068, unescape...
			s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
		}

		try {
			// Replace server-side written pluses with spaces.
			// If we can't decode the cookie, ignore it, it's unusable.
			// If we can't parse the cookie, ignore it, it's unusable.
			s = decodeURIComponent(s.replace(pluses, ' '));
			return config.json ? JSON.parse(s) : s;
		} catch(e) {}
	}

	function read(s, converter) {
		var value = config.raw ? s : parseCookieValue(s);
		return $.isFunction(converter) ? converter(value) : value;
	}

	var config = $.cookie = function (key, value, options) {

		// Write

		if (arguments.length > 1 && !$.isFunction(value)) {
			options = $.extend({}, config.defaults, options);

			if (typeof options.expires === 'number') {
				var days = options.expires, t = options.expires = new Date();
				t.setMilliseconds(t.getMilliseconds() + days * 864e+5);
			}

			return (document.cookie = [
				encode(key), '=', stringifyCookieValue(value),
				options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
				options.path    ? '; path=' + options.path : '',
				options.domain  ? '; domain=' + options.domain : '',
				options.secure  ? '; secure' : ''
			].join(''));
		}

		// Read

		var result = key ? undefined : {},
			// To prevent the for loop in the first place assign an empty array
			// in case there are no cookies at all. Also prevents odd result when
			// calling $.cookie().
			cookies = document.cookie ? document.cookie.split('; ') : [],
			i = 0,
			l = cookies.length;

		for (; i < l; i++) {
			var parts = cookies[i].split('='),
				name = decode(parts.shift()),
				cookie = parts.join('=');

			if (key === name) {
				// If second argument (value) is a function it's a converter...
				result = read(cookie, value);
				break;
			}

			// Prevent storing a cookie that we couldn't decode.
			if (!key && (cookie = read(cookie)) !== undefined) {
				result[name] = cookie;
			}
		}

		return result;
	};

	config.defaults = {};

	$.removeCookie = function (key, options) {
		// Must not alter options, thus extending a fresh object...
		$.cookie(key, '', $.extend({}, options, { expires: -1 }));
		return !$.cookie(key);
	};

}));

/* Issue #924 is fixed for 5.3+ and 6.0. (compatibility with 5.3) */
if(window['PrimeFaces'] && window['PrimeFaces'].widget.Dialog) {
    PrimeFaces.widget.Dialog = PrimeFaces.widget.Dialog.extend({
        
        enableModality: function() {
            this._super();
            $(document.body).children(this.jqId + '_modal').addClass('ui-dialog-mask');
        },
        
        syncWindowResize: function() {}
    });
}

/* Issue #2131 */
if(window['PrimeFaces'] && window['PrimeFaces'].widget.Schedule && isLtPF8Version()) {
    PrimeFaces.widget.Schedule = PrimeFaces.widget.Schedule.extend({
        
        setupEventSource: function() {
            var $this = this,
            offset = moment().utcOffset()*60000;

            this.cfg.events = function(start, end, timezone, callback) {
                var options = {
                    source: $this.id,
                    process: $this.id,
                    update: $this.id,
                    formId: $this.cfg.formId,
                    params: [
                        {name: $this.id + '_start', value: start.valueOf() + offset},
                        {name: $this.id + '_end', value: end.valueOf() + offset}
                    ],
                    onsuccess: function(responseXML, status, xhr) {
                        PrimeFaces.ajax.Response.handle(responseXML, status, xhr, {
                                widget: $this,
                                handle: function(content) {
                                    callback($.parseJSON(content).events);
                                }
                            });

                        return true;
                    }
                };

                PrimeFaces.ajax.Request.handle(options);
            }
        }
    });
}

function isLtPF8Version() {
    var version = window['PrimeFaces'].VERSION;
    if (!version) {
        return true;
    }

    return parseInt(version.split('.')[0], 10) < 8;
}

if (PrimeFaces.widget.SelectOneMenu) {
    PrimeFaces.widget.SelectOneMenu = PrimeFaces.widget.SelectOneMenu.extend({
        init: function (cfg) {
            this._super(cfg);

            var $this = this;
            if (this.jq.parent().hasClass('ui-float-label')) {
                this.m_panel = $(this.jqId + '_panel');
                this.m_focusInput = $(this.jqId + '_focus');

                this.m_panel.addClass('ui-input-overlay-panel');
                this.jq.addClass('ui-inputwrapper');

                if (this.input.val() != '') {
                    this.jq.addClass('ui-inputwrapper-filled');
                }

                this.input.off('change').on('change', function () {
                    $this.inputValueControl($(this));
                });

                this.m_focusInput.on('focus.ui-selectonemenu', function () {
                    $this.jq.addClass('ui-inputwrapper-focus');
                })
                    .on('blur.ui-selectonemenu', function () {
                        $this.jq.removeClass('ui-inputwrapper-focus');
                    });

                if (this.cfg.editable) {
                    this.label.on('input', function (e) {
                        $this.inputValueControl($(this));
                    }).on('focus', function () {
                        $this.jq.addClass('ui-inputwrapper-focus');
                    }).on('blur', function () {
                        $this.jq.removeClass('ui-inputwrapper-focus');
                        $this.inputValueControl($(this));
                    });
                }
            }
        },

        inputValueControl: function (input) {
            if (input.val() != '')
                this.jq.addClass('ui-inputwrapper-filled');
            else
                this.jq.removeClass('ui-inputwrapper-filled');
        }
    });
}

if (PrimeFaces.widget.Chips) {
    PrimeFaces.widget.Chips = PrimeFaces.widget.Chips.extend({
        init: function (cfg) {
            this._super(cfg);

            var $this = this;
            if (this.jq.parent().hasClass('ui-float-label')) {
                this.jq.addClass('ui-inputwrapper');

                if ($this.jq.find('.ui-chips-token').length !== 0) {
                    this.jq.addClass('ui-inputwrapper-filled');
                }

                this.input.on('focus.ui-chips', function () {
                    $this.jq.addClass('ui-inputwrapper-focus');
                }).on('input.ui-chips', function () {
                    $this.inputValueControl();
                }).on('blur.ui-chips', function () {
                    $this.jq.removeClass('ui-inputwrapper-focus');
                    $this.inputValueControl();
                });

            }
        },

        inputValueControl: function () {
            if (this.jq.find('.ui-chips-token').length !== 0 || this.input.val() != '')
                this.jq.addClass('ui-inputwrapper-filled');
            else
                this.jq.removeClass('ui-inputwrapper-filled');
        }
    });
}

if (PrimeFaces.widget.DatePicker) {
    PrimeFaces.widget.DatePicker = PrimeFaces.widget.DatePicker.extend({
        init: function (cfg) {
            this._super(cfg);

            var $this = this;
            if (this.jq.parent().hasClass('ui-float-label') && !this.cfg.inline) {
                if (this.input.val() != '') {
                    this.jq.addClass('ui-inputwrapper-filled');
                }

                this.jqEl.off('focus.ui-datepicker blur.ui-datepicker change.ui-datepicker')
                    .on('focus.ui-datepicker', function () {
                        $this.jq.addClass('ui-inputwrapper-focus');
                    })
                    .on('blur.ui-datepicker', function () {
                        $this.jq.removeClass('ui-inputwrapper-focus');
                    })
                    .on('change.ui-datepicker', function () {
                        $this.inputValueControl($(this));
                    });
            }
        },

        inputValueControl: function (input) {
            if (input.val() != '')
                this.jq.addClass('ui-inputwrapper-filled');
            else
                this.jq.removeClass('ui-inputwrapper-filled');
        }
    });
}